package com.onix.internship.ui.editor.view

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.text.Editable
import android.text.InputType
import android.text.Selection
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.BaseInputConnection
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputMethodManager
import androidx.databinding.BindingAdapter
import com.onix.internship.R
import com.onix.internship.ui.editor.EditorModeTypes
import com.onix.internship.ui.editor.data.TextResizeMode
import com.onix.internship.ui.editor.data.UserEditorActions
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class ImageEditorView : View {
    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val inputMethodManager by lazy {
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    }

    private val mGestureDetector: GestureDetector by lazy {
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                if (textResizeMode == TextResizeMode.NONE) {
                    checkSelectedCorner(e1)
                }

                when (textResizeMode) {
                    TextResizeMode.TEXT_MOVE -> {
                        startPoint.set(
                            startPoint.x + (distanceX * -1),
                            startPoint.y + (distanceY * -1)
                        )
                        endPoint.set(
                            endPoint.x + (distanceX * -1),
                            endPoint.y + (distanceY * -1)
                        )
                    }

                    else -> checkCanResizeTextBox(PointF(e2.x, e2.y), textResizeMode)
                }
                return super.onScroll(e1, e2, distanceX, distanceY)
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                if (e.x >= startPoint.x &&
                    e.x <= endPoint.x &&
                    e.y >= startPoint.y &&
                    e.y <= endPoint.y
                ) {
                    if (!keyboardVisible) {
                        if (!isFocused) requestFocus()
                        showKeyboard()
                    }
                } else {
                    if (keyboardVisible) {
                        hideKeyboard()
                    } else {
                        saveTextPrinting()
                    }
                }
                return super.onSingleTapUp(e)
            }
        })
    }

    private fun saveTextPrinting() {
        val action = UserEditorActions.TextDraw(
            startPoint = PointF(startPoint.x, startPoint.y),
            endPoint = PointF(endPoint.x, endPoint.y),
            text = text,
            textSize = textSize,
            brushColor = paintColor
        )

        startPoint.set(0f, 0f)
        endPoint.set(0f, 0f)
        mEditable.clear()
        text = ""
        mode = EditorModeTypes.NONE
        modeChangeListener?.onModeChangedListener(mode)

        if (action.text.isNotEmpty()) {
            actionsList.add(action)
            currentLastActionId++
            drawShape(listOf(action))
        } else {
            invalidate()
        }
    }

    private fun checkSelectedCorner(e1: MotionEvent) {
        textResizeMode = when {
            e1.x <= startPoint.x + EDITOR_CIRCLE_RADIUS &&
                    e1.x >= startPoint.x - EDITOR_CIRCLE_RADIUS &&
                    e1.y <= startPoint.y + EDITOR_CIRCLE_RADIUS &&
                    e1.y >= startPoint.y - EDITOR_CIRCLE_RADIUS -> {
                TextResizeMode.LEFT_TOP
            }

            e1.x <= endPoint.x + EDITOR_CIRCLE_RADIUS &&
                    e1.x >= endPoint.x - EDITOR_CIRCLE_RADIUS &&
                    e1.y <= endPoint.y + EDITOR_CIRCLE_RADIUS &&
                    e1.y >= endPoint.y - EDITOR_CIRCLE_RADIUS -> {
                TextResizeMode.RIGHT_BOTTOM
            }

            e1.x <= startPoint.x + EDITOR_CIRCLE_RADIUS &&
                    e1.x >= startPoint.x - EDITOR_CIRCLE_RADIUS &&
                    e1.y <= endPoint.y + EDITOR_CIRCLE_RADIUS &&
                    e1.y >= endPoint.y - EDITOR_CIRCLE_RADIUS -> {
                TextResizeMode.LEFT_BOTTOM
            }

            e1.x <= endPoint.x + EDITOR_CIRCLE_RADIUS &&
                    e1.x >= endPoint.x - EDITOR_CIRCLE_RADIUS &&
                    e1.y <= startPoint.y + EDITOR_CIRCLE_RADIUS &&
                    e1.y >= startPoint.y - EDITOR_CIRCLE_RADIUS -> {
                TextResizeMode.RIGHT_TOP
            }

            e1.x >= startPoint.x && e1.x <= endPoint.x && e1.y >= startPoint.y && e1.y <= endPoint.y -> {
                TextResizeMode.TEXT_MOVE
            }

            else -> TextResizeMode.NONE
        }
    }

    private fun checkCanResizeTextBox(
        endPosition: PointF,
        corner: TextResizeMode
    ) {
        val textPaint = Paint().apply {
            isAntiAlias = true
            color = paintColor
            textSize = textSize
        }

        val linesCount = getTextLines(text, textPaint, startPoint, endPoint).size
        val count = if (linesCount > 0) linesCount else 1
        val textBoxHeight = textSize * count + TEXT_PADDING

        when (corner) {
            TextResizeMode.LEFT_TOP -> {
                val newW = endPoint.x - endPosition.x
                val newH = endPoint.y - endPosition.y

                startPoint.x = if (newW < MIN_TEXT_BOX_WIDTH) {
                    endPoint.x - MIN_TEXT_BOX_WIDTH
                } else endPosition.x

                startPoint.y = if (newH < textBoxHeight) {
                    endPoint.y - textBoxHeight
                } else endPosition.y
            }

            TextResizeMode.RIGHT_BOTTOM -> {
                val newW = endPosition.x - startPoint.x
                val newH = endPosition.y - startPoint.y

                endPoint.x = if (newW < MIN_TEXT_BOX_WIDTH) {
                    startPoint.x + MIN_TEXT_BOX_WIDTH
                } else endPosition.x

                endPoint.y = if (newH < textBoxHeight) {
                    startPoint.y + textBoxHeight
                } else endPosition.y
            }

            TextResizeMode.LEFT_BOTTOM -> {
                val newW = endPoint.x - endPosition.x
                val newH = endPosition.y - startPoint.y

                startPoint.x = if (newW < MIN_TEXT_BOX_WIDTH) {
                    endPoint.x - MIN_TEXT_BOX_WIDTH
                } else endPosition.x

                endPoint.y = if (newH < textBoxHeight) {
                    startPoint.y + textBoxHeight
                } else endPosition.y
            }

            TextResizeMode.RIGHT_TOP -> {
                val newW = endPosition.x - startPoint.x
                val newH = endPoint.y - endPosition.y

                endPoint.x = if (newW < MIN_TEXT_BOX_WIDTH) {
                    startPoint.x + MIN_TEXT_BOX_WIDTH
                } else endPosition.x

                startPoint.y = if (newH < textBoxHeight) {
                    endPoint.y - textBoxHeight
                } else endPosition.y
            }

            else -> {}
        }
    }

    //view draw image variables
    private var image: Bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    private var drawBitmap: Bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    private var imagePositionRect: Rect = Rect()
    private var imageScale: Float = 1f
    private var textSize: Float = resources.getDimensionPixelSize(R.dimen.text_16).toFloat()
    private var brushSize: Float = resources.getDimensionPixelSize(R.dimen.width_4).toFloat()

    private var modeChangeListener: OnModeChanged? = null

    //view temp variables
    private var actionsList = mutableListOf<UserEditorActions>()
    private var currentLastActionId: Int = actionsList.lastIndex
    private var startPoint: PointF = PointF(0f, 0f)
    private var endPoint: PointF = PointF(0f, 0f)
    private var pointsList = mutableListOf<PointF>() //for DRAW mode
    private var mEditable = SpannableStringBuilder("")
    private var text: String = ""

    private var textResizeMode: TextResizeMode = TextResizeMode.NONE
    private var keyboardVisible: Boolean = false

    private var canUseUndo: Boolean = false
    private var canUseRedo: Boolean = false

    private var undoChangeListener: OnUndoStatusChanged? = null
    private var redoChangeListener: OnRedoStatusChanged? = null
    private var imageChangedListener: OnImageChanged? = null

    //
    //view state
    private var mode: EditorModeTypes = EditorModeTypes.RECTANGLE
    private var paintColor: Int = Color.RED

    private var redrawPaint: Boolean = false
    private var viewCreated: Boolean = true
    private var configChanged: Boolean = false

    private val mMyTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            text = s.toString()
            invalidate()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    init {
        isFocusableInTouchMode = true
        isFocusable = true
        isSaveEnabled = true

        setupEditable()
        setupKeyboardListener()
    }

    //public functions
    fun setImageBitmap(bm: Bitmap?) {
        if (bm != null) {
            image = bm
            viewCreated = true
            invalidate()
        }
    }

    fun setEditorMode(editorMode: EditorModeTypes?) {
        if (editorMode != null) {
            if (editorMode != EditorModeTypes.TEXT && mode == EditorModeTypes.TEXT) {
                saveTextPrinting()
            }

            mode = editorMode

            if (editorMode == EditorModeTypes.TEXT) {
                val centerPoint = PointF(width / 2f, height / 3f)
                startPoint =
                    PointF(
                        centerPoint.x - MIN_TEXT_BOX_WIDTH / 2,
                        centerPoint.y - textSize / 2 - TEXT_PADDING
                    )
                endPoint =
                    PointF(
                        centerPoint.x + MIN_TEXT_BOX_WIDTH / 2,
                        centerPoint.y + textSize / 2 + TEXT_PADDING
                    )

                if (!isFocused) requestFocus()

                showKeyboard()
                invalidate()
            }
        }
    }

    fun setBrushColor(color: Int?) {
        if (color != null) {
            paintColor = color

            if (mode == EditorModeTypes.TEXT) {
                invalidate()
            }
        }
    }

    fun undo() {
        if (currentLastActionId != -1) {
            drawBitmap = Bitmap.createBitmap(
                imagePositionRect.width(),
                imagePositionRect.height(),
                Bitmap.Config.ARGB_8888
            )
            currentLastActionId--
            redrawPaint = true

            undoChangeListener?.onUndoStatusChangedListener(canUseUndo)
            redoChangeListener?.onRedoStatusChangedListener(canUseRedo)
            updateImageChangedState()

            invalidate()
        }
    }

    fun redo() {
        if (currentLastActionId != actionsList.lastIndex) {
            currentLastActionId++
            redrawPaint = true

            canUseRedo = currentLastActionId != actionsList.lastIndex
            canUseUndo = true

            undoChangeListener?.onUndoStatusChangedListener(canUseUndo)
            redoChangeListener?.onRedoStatusChangedListener(canUseRedo)
            updateImageChangedState()

            invalidate()
        }
    }

    fun saveImage(): Bitmap {
        val bitmap = Bitmap.createBitmap(
            imagePositionRect.width(),
            imagePositionRect.height(),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)

        val drawRect = Rect(0, 0, imagePositionRect.width(), imagePositionRect.height())
        canvas.drawBitmap(image, null, drawRect, null)
        canvas.drawBitmap(drawBitmap, null, drawRect, null)

        return bitmap
    }

    //main logic
    override fun onDraw(canvas: Canvas) {
        if (viewCreated) {
            setupStartParams()
            viewCreated = false
        }

        if (configChanged) {
            val oldImagePosition = imagePositionRect
            setupStartParams()
            configActionsAfterRotationScreen(imagePositionRect, oldImagePosition)
            configChanged = false
        }

        val upperLayer = onPrepareOwnCanvas()
        canvas.drawBitmap(image, null, imagePositionRect, null)
        canvas.drawBitmap(upperLayer, null, imagePositionRect, null)
        drawTempFigure(canvas)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        if (keyboardVisible)
            hideKeyboard()

        redrawPaint = true
        configChanged = true
        invalidate()
    }

    private fun configActionsAfterRotationScreen(newRect: Rect, oldRect: Rect) {
        if (oldRect != Rect() && newRect != oldRect && actionsList.isNotEmpty()) {
            val scale: Float = newRect.width().toFloat() / oldRect.width()
            val list = actionsList.map {
                when (it) {
                    is UserEditorActions.SimpleDrawAction -> {
                        val newStart = newPointPosition(it.startPoint, oldRect, newRect, scale)
                        val newEnd = newPointPosition(it.endPoint, oldRect, newRect, scale)
                        val newBrushSize = it.brushSize * scale

                        UserEditorActions.SimpleDrawAction(
                            startPoint = newStart,
                            endPoint = newEnd,
                            brushColor = it.brushColor,
                            brushSize = newBrushSize,
                            action = it.action
                        )
                    }

                    is UserEditorActions.FigureDraw -> {
                        val newPointsList = it.points.map { point ->
                            newPointPosition(point, oldRect, newRect, scale)
                        }
                        val newBrushSize = it.brushSize * scale

                        UserEditorActions.FigureDraw(
                            points = newPointsList,
                            brushColor = it.brushColor,
                            brushSize = newBrushSize
                        )
                    }

                    is UserEditorActions.TextDraw -> {
                        val newStart = newPointPosition(it.startPoint, oldRect, newRect, scale)
                        val newEnd = newPointPosition(it.endPoint, oldRect, newRect, scale)
                        val textSize = it.textSize * scale

                        UserEditorActions.TextDraw(
                            startPoint = newStart,
                            endPoint = newEnd,
                            text = it.text,
                            textSize = textSize,
                            brushColor = it.brushColor
                        )
                    }
                }
            }

            startPoint.set(newPointPosition(startPoint, oldRect, newRect, scale))
            endPoint.set(newPointPosition(endPoint, oldRect, newRect, scale))
            textSize *= scale
            brushSize *= scale

            actionsList.clear()
            actionsList.addAll(list)
        }
    }

    private fun newPointPosition(
        point: PointF,
        oldRect: Rect,
        newRect: Rect,
        scale: Float
    ): PointF {
        val x: Float = calculateNewPointPosition(
            pointCoordinate = point.x,
            oldStart = oldRect.left,
            oldEnd = oldRect.right,
            newStart = newRect.left,
            newEnd = newRect.right,
            scale = scale
        )

        val y: Float = calculateNewPointPosition(
            pointCoordinate = point.y,
            oldStart = oldRect.top,
            oldEnd = oldRect.bottom,
            newStart = newRect.top,
            newEnd = newRect.bottom,
            scale = scale
        )

        return PointF(x, y)
    }

    private fun calculateNewPointPosition(
        pointCoordinate: Float,
        oldStart: Int,
        oldEnd: Int,
        newStart: Int,
        newEnd: Int,
        scale: Float
    ): Float {
        return if (pointCoordinate < oldStart) {
            val d = oldStart - pointCoordinate
            newStart - d * scale
        } else if (pointCoordinate >= oldStart && pointCoordinate <= oldEnd) {
            val d = pointCoordinate - oldStart
            newStart + d * scale
        } else {
            val d = pointCoordinate - oldEnd
            newEnd + d * scale
        }
    }

    private fun drawTempFigure(canvas: Canvas) {
        val tempShape = UserEditorActions.SimpleDrawAction(
            startPoint = startPoint,
            endPoint = endPoint,
            brushColor = paintColor,
            brushSize = brushSize,
            action = mode
        )
        showShape(canvas, tempShape)

        val tempDraw = UserEditorActions.FigureDraw(
            points = pointsList,
            brushColor = paintColor,
            brushSize = brushSize
        )
        showFingerDraw(canvas, tempDraw)

        if (mode == EditorModeTypes.TEXT) {
            val tempTextAction =
                UserEditorActions.TextDraw(
                    startPoint = startPoint,
                    endPoint = endPoint,
                    text = text,
                    textSize = textSize,
                    brushColor = paintColor
                )
            showTextDraw(canvas, tempTextAction, true)
        }
    }

    private fun onPrepareOwnCanvas(): Bitmap {
        if (redrawPaint) {
            drawShape(actionsList)
            redrawPaint = false
        }
        return drawBitmap
    }

    private fun setupStartParams() {
        imageScale = min(width.toFloat() / image.width, height.toFloat() / image.height)

        val w = (image.width * imageScale).toInt()
        val h = (image.height * imageScale).toInt()

        imagePositionRect = Rect(
            (width - w) / 2,
            (height - h) / 2,
            (width - w) / 2 + w,
            (height - h) / 2 + h
        )

        drawBitmap = Bitmap.createBitmap(
            imagePositionRect.width(),
            imagePositionRect.height(),
            Bitmap.Config.ARGB_8888
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        checkHistory()
        when (mode) {
            EditorModeTypes.RECTANGLE,
            EditorModeTypes.LINE,
            EditorModeTypes.ARROW -> modeDrawShape(event)

            EditorModeTypes.DRAW -> modeFingerDraw(event)
            EditorModeTypes.TEXT -> modeTextDraw(event)
            else -> {}
        }

        invalidate()
        return true
    }

    private fun checkHistory() {
        if (currentLastActionId != actionsList.lastIndex && currentLastActionId >= 0) {
            actionsList.removeIf {
                val index = actionsList.indexOf(it)
                index > currentLastActionId
            }
        } else if (currentLastActionId == -1 && actionsList.isNotEmpty()) {
            actionsList.clear()
        }
        currentLastActionId = actionsList.lastIndex

        canUseUndo = true
        canUseRedo = false

        undoChangeListener?.onUndoStatusChangedListener(canUseUndo)
        redoChangeListener?.onRedoStatusChangedListener(canUseRedo)
    }

    private fun modeTextDraw(event: MotionEvent) {
        if (!mGestureDetector.onTouchEvent(event) && event.action == MotionEvent.ACTION_UP) {
            textResizeMode = TextResizeMode.NONE
        }
    }

    private fun modeFingerDraw(event: MotionEvent) {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                pointsList.add(PointF(event.x, event.y))
            }

            MotionEvent.ACTION_UP -> {
                pointsList.add(PointF(event.x, event.y))
                val newList = pointsList.map { PointF(it.x, it.y) }
                pointsList.clear()

                val action = UserEditorActions.FigureDraw(
                    points = newList,
                    brushColor = paintColor,
                    brushSize = brushSize
                )
                actionsList.add(action)
                currentLastActionId++
                drawShape(listOf(action))
            }
        }
    }

    private fun modeDrawShape(event: MotionEvent) {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                startPoint.set(event.x, event.y)
                endPoint.set(event.x, event.y)
            }

            MotionEvent.ACTION_MOVE -> {
                endPoint.set(event.x, event.y)
            }

            MotionEvent.ACTION_UP -> {
                val action = UserEditorActions.SimpleDrawAction(
                    startPoint = PointF(startPoint.x, startPoint.y),
                    endPoint = PointF(endPoint.x, endPoint.y),
                    brushColor = paintColor,
                    brushSize = brushSize,
                    action = mode
                )

                actionsList.add(action)
                currentLastActionId++
                drawShape(listOf(action))
                startPoint.set(0f, 0f)
                endPoint.set(0f, 0f)
            }
        }
    }

    //crop image by original sizes to correctly draw shapes
    private fun drawShape(shapeList: List<UserEditorActions>) {
        //draw originally shapes
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        canvas.drawBitmap(drawBitmap, null, imagePositionRect, null)

        shapeList.forEachIndexed { index, action ->
            if (index <= currentLastActionId) {
                when (action) {
                    is UserEditorActions.SimpleDrawAction -> showShape(canvas, action)
                    is UserEditorActions.FigureDraw -> showFingerDraw(canvas, action)
                    is UserEditorActions.TextDraw -> showTextDraw(canvas, action, false)
                }
            }
        }

        //crop to correct size and result
        val resultBitmap = Bitmap.createBitmap(
            imagePositionRect.width(),
            imagePositionRect.height(),
            Bitmap.Config.ARGB_8888
        )
        val resultCanvas = Canvas(resultBitmap)

        val leftCrop = imagePositionRect.left * (-1)
        val topCrop = imagePositionRect.top * (-1)

        val cropRect = Rect(
            leftCrop,
            topCrop,
            leftCrop + width,
            topCrop + height
        )

        resultCanvas.drawBitmap(bitmap, null, cropRect, null)

        drawBitmap = resultBitmap
        updateImageChangedState()
    }

    private fun showTextDraw(
        canvas: Canvas,
        action: UserEditorActions.TextDraw,
        editable: Boolean
    ) {
        val textPaint = Paint().apply {
            isAntiAlias = true
            color = action.brushColor
            textSize = action.textSize
        }

        val linesList = getTextLines(action.text, textPaint, action.startPoint, action.endPoint)
        val lineCount = linesList.size

        linesList.forEachIndexed { index, it ->
            canvas.drawText(
                it,
                action.startPoint.x + TEXT_PADDING,
                action.startPoint.y + action.textSize * (index + 1),
                textPaint
            )
        }

        if (editable) {

            if (abs(action.endPoint.y - action.startPoint.y) < action.textSize * lineCount + TEXT_PADDING) {
                if (action.startPoint.y < action.endPoint.y) {
                    action.endPoint.y =
                        action.startPoint.y + action.textSize * lineCount + TEXT_PADDING
                } else {
                    action.startPoint.y =
                        action.endPoint.y + action.textSize * lineCount + TEXT_PADDING
                }
            }

            val boxColor = resources.getColor(R.color.colorTextSecondary, null)

            val rectPaint = Paint().apply {
                style = Paint.Style.STROKE
                color = boxColor
                strokeWidth = STROKE_WIDTH
                pathEffect = DashPathEffect(floatArrayOf(20f, 20f), 0f)
            }

            val cornerFilledPaint = Paint().apply {
                color = boxColor
                style = Paint.Style.FILL
            }

            val cornerStrokePaint = Paint().apply {
                color = Color.WHITE
                style = Paint.Style.STROKE
                strokeWidth = STROKE_WIDTH
            }

            //editor rect
            canvas.drawRect(
                Rect(
                    action.startPoint.x.toInt(),
                    action.startPoint.y.toInt(),
                    action.endPoint.x.toInt(),
                    action.endPoint.y.toInt()
                ), rectPaint
            )

            //left top corner
            drawLeftTopCorner(canvas, action, cornerFilledPaint)
            drawLeftTopCorner(canvas, action, cornerStrokePaint)

            //right top corner
            drawRightTopCorner(canvas, action, cornerFilledPaint)
            drawRightTopCorner(canvas, action, cornerStrokePaint)

            //right bottom corner
            drawRightBottomCorner(canvas, action, cornerFilledPaint)
            drawRightBottomCorner(canvas, action, cornerStrokePaint)

            //left bottom corner
            drawLeftBottomCorner(canvas, action, cornerFilledPaint)
            drawLeftBottomCorner(canvas, action, cornerStrokePaint)
        }
    }

    private fun getTextLines(text: String, paint: Paint, start: PointF, end: PointF): List<String> {
        val textLines = text.split("\n")
        val resultList = mutableListOf<String>()

        textLines.forEach {
            var textStartPos = 0
            var textEndPos = 0
            val endPos = it.length

            var lineCount = 0

            while (textStartPos < endPos) {
                textEndPos += paint.breakText(
                    it,
                    true,
                    abs(end.x - start.x) - TEXT_PADDING * 2,
                    null
                )
                lineCount++

                resultList.add(
                    if (textEndPos <= it.lastIndex) {
                        it.substring(textStartPos, textEndPos)
                    } else {
                        it.substring(textStartPos, it.length)
                    }
                )

                textStartPos = textEndPos
            }
        }

        if (text.isNotEmpty() && text.last().toString() == "\u000A") {
            resultList.add("")
        }

        return resultList
    }

    private fun drawLeftTopCorner(
        canvas: Canvas,
        action: UserEditorActions.TextDraw,
        paint: Paint
    ) {
        canvas.drawOval(
            RectF(
                action.startPoint.x - EDITOR_CIRCLE_RADIUS,
                action.startPoint.y - EDITOR_CIRCLE_RADIUS,
                action.startPoint.x + EDITOR_CIRCLE_RADIUS,
                action.startPoint.y + EDITOR_CIRCLE_RADIUS
            ), paint
        )
    }

    private fun drawRightTopCorner(
        canvas: Canvas,
        action: UserEditorActions.TextDraw,
        paint: Paint
    ) {
        canvas.drawOval(
            RectF(
                action.endPoint.x - EDITOR_CIRCLE_RADIUS,
                action.startPoint.y - EDITOR_CIRCLE_RADIUS,
                action.endPoint.x + EDITOR_CIRCLE_RADIUS,
                action.startPoint.y + EDITOR_CIRCLE_RADIUS
            ), paint
        )
    }

    private fun drawRightBottomCorner(
        canvas: Canvas,
        action: UserEditorActions.TextDraw,
        paint: Paint
    ) {
        canvas.drawOval(
            RectF(
                action.endPoint.x - EDITOR_CIRCLE_RADIUS,
                action.endPoint.y - EDITOR_CIRCLE_RADIUS,
                action.endPoint.x + EDITOR_CIRCLE_RADIUS,
                action.endPoint.y + EDITOR_CIRCLE_RADIUS,
            ), paint
        )
    }

    private fun drawLeftBottomCorner(
        canvas: Canvas,
        action: UserEditorActions.TextDraw,
        paint: Paint
    ) {
        canvas.drawOval(
            RectF(
                action.startPoint.x - EDITOR_CIRCLE_RADIUS,
                action.endPoint.y - EDITOR_CIRCLE_RADIUS,
                action.startPoint.x + EDITOR_CIRCLE_RADIUS,
                action.endPoint.y + EDITOR_CIRCLE_RADIUS,
            ), paint
        )
    }

    //show and update figure while user draw and don't apply result
    private fun showFingerDraw(canvas: Canvas, action: UserEditorActions.FigureDraw) {
        val path = Path()
        action.points.forEachIndexed { index, pointF ->
            if (index == 0) {
                path.moveTo(pointF.x, pointF.y)
            } else {
                path.lineTo(pointF.x, pointF.y)
            }
        }

        val paint = Paint().apply {
            color = action.brushColor
            strokeWidth = brushSize
            style = Paint.Style.STROKE
        }

        canvas.drawPath(path, paint)
    }

    //show and update shape while user draw and don't apply result
    private fun showShape(
        canvas: Canvas,
        data: UserEditorActions.SimpleDrawAction
    ) {
        when (data.action) {
            EditorModeTypes.RECTANGLE -> showRect(canvas, data)
            EditorModeTypes.LINE -> showLine(canvas, data)
            EditorModeTypes.ARROW -> showArrow(canvas, data)
            else -> {}
        }
    }

    private fun showLine(canvas: Canvas, data: UserEditorActions.SimpleDrawAction) {
        val linePaint = Paint().apply {
            color = data.brushColor
            strokeWidth = brushSize
        }

        canvas.drawLine(
            data.startPoint.x,
            data.startPoint.y,
            data.endPoint.x,
            data.endPoint.y,
            linePaint
        )
    }

    private fun showRect(canvas: Canvas, data: UserEditorActions.SimpleDrawAction) {
        val rectPaint = Paint().apply {
            color = data.brushColor
            strokeWidth = brushSize
            style = Paint.Style.STROKE
        }

        canvas.drawRect(
            data.startPoint.x,
            data.startPoint.y,
            data.endPoint.x,
            data.endPoint.y,
            rectPaint
        )
    }

    private fun showArrow(canvas: Canvas, data: UserEditorActions.SimpleDrawAction) {
        val arrowPaint = Paint().apply {
            color = data.brushColor
            strokeWidth = brushSize
            style = Paint.Style.FILL
        }

        val arrowTriangleShape = makeArrowTriangle(
            start = data.startPoint,
            end = data.endPoint,
            triangleHeight = data.brushSize * 3
        )

        canvas.drawLine(
            data.startPoint.x,
            data.startPoint.y,
            data.endPoint.x,
            data.endPoint.y,
            arrowPaint
        )
        canvas.drawPath(arrowTriangleShape, arrowPaint)
    }

    private fun makeArrowTriangle(start: PointF, end: PointF, triangleHeight: Float): Path {
        var inRads: Double = atan2(end.y - start.y, end.x - start.x).toDouble()

        if (inRads < 0) inRads = 2 * PI - abs(inRads)

        val angle = Math.toDegrees(inRads)

        val path = Path()

        val topX = (end.x + triangleHeight / 2 * cos(Math.toRadians(angle))).toFloat()
        val topY = (end.y + triangleHeight / 2 * sin(Math.toRadians(angle))).toFloat()

        path.moveTo(topX, topY)

        val firstX = (topX - (triangleHeight * cos(Math.toRadians(angle - 30)))).toFloat()
        val firstY = (topY - (triangleHeight * sin(Math.toRadians(angle - 30)))).toFloat()

        val secondX = (topX - (triangleHeight * cos(Math.toRadians(angle + 30)))).toFloat()
        val secondY = (topY - (triangleHeight * sin(Math.toRadians(angle + 30)))).toFloat()

        path.lineTo(firstX, firstY)
        path.lineTo(secondX, secondY)

        path.close()

        return path
    }

    private fun updateImageChangedState() {
        imageChangedListener?.onImageChangedListener(actionsList.isNotEmpty() || currentLastActionId != -1)
    }

    //text editor
    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        outAttrs.apply {
            inputType = InputType.TYPE_CLASS_TEXT
            imeOptions = EditorInfo.IME_ACTION_UNSPECIFIED
            initialSelStart = getSelectionStart()
            initialSelEnd = getSelectionEnd()
        }

        return object : BaseInputConnection(this, true) {
            override fun getEditable(): Editable {
                return mEditable
            }
        }
    }

    private fun getSelectionStart(): Int {
        return Selection.getSelectionStart(mEditable)
    }

    private fun getSelectionEnd(): Int {
        return Selection.getSelectionEnd(mEditable)
    }

    override fun onCheckIsTextEditor(): Boolean {
        return true
    }

    private fun showKeyboard() {
        try {
            inputMethodManager?.apply {
                updateSelection(this@ImageEditorView, 0, mEditable.length, -1, -1)
                showSoftInput(this@ImageEditorView, InputMethodManager.SHOW_IMPLICIT)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun hideKeyboard() {
        inputMethodManager?.hideSoftInputFromWindow(
            windowToken,
            InputMethodManager.SHOW_IMPLICIT
        )
    }

    private fun setupEditable() {
        mEditable = SpannableStringBuilder(text)
        Selection.setSelection(mEditable, mEditable.length)
        mEditable.setSpan(mMyTextWatcher, 0, mEditable.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    }

    private fun setupKeyboardListener() {
        (context as? Activity)?.let {
            KeyboardVisibilityEvent.registerEventListener(it) { isOpen ->
                keyboardVisible = isOpen
            }
        }
    }

    companion object {
        const val EDITOR_CIRCLE_RADIUS = 25
        const val TEXT_PADDING = 20
        const val STROKE_WIDTH = 5f
        const val MIN_TEXT_BOX_WIDTH = 600
    }

    fun setUndoChangeListener(listener: (Boolean) -> Unit) {
        undoChangeListener = object : OnUndoStatusChanged {
            override fun onUndoStatusChangedListener(canUseUndo: Boolean) {
                listener.invoke(canUseUndo)
            }
        }
    }

    fun setRedoChangeListener(listener: (Boolean) -> Unit) {
        redoChangeListener = object : OnRedoStatusChanged {
            override fun onRedoStatusChangedListener(canUseRedo: Boolean) {
                listener.invoke(canUseRedo)
            }
        }
    }

    fun setOnImageChangedListener(listener: (Boolean) -> Unit) {
        imageChangedListener = object : OnImageChanged {
            override fun onImageChangedListener(changed: Boolean) {
                listener.invoke(changed)
            }
        }
    }

    fun setModeChangeListener(listener: (EditorModeTypes) -> Unit) {
        modeChangeListener = object : OnModeChanged {
            override fun onModeChangedListener(mode: EditorModeTypes) {
                listener.invoke(mode)
            }
        }
    }

    interface OnUndoStatusChanged {
        fun onUndoStatusChangedListener(canUseUndo: Boolean)
    }

    interface OnRedoStatusChanged {
        fun onRedoStatusChangedListener(canUseRedo: Boolean)
    }

    interface OnImageChanged {
        fun onImageChangedListener(changed: Boolean)
    }

    interface OnModeChanged {
        fun onModeChangedListener(mode: EditorModeTypes)
    }
}

@BindingAdapter("editorMode")
fun ImageEditorView.setImageEditorMode(editorMode: EditorModeTypes?) {
    this.setEditorMode(editorMode)
}

@BindingAdapter("brushColor")
fun ImageEditorView.setEditorBrushColor(brushColor: Int?) {
    setBrushColor(brushColor)
}
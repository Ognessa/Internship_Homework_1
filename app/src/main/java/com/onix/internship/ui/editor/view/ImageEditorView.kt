package com.onix.internship.ui.editor.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.databinding.BindingAdapter
import com.onix.internship.ui.editor.EditorModeTypes
import com.onix.internship.ui.editor.data.UserEditorActions
import java.io.Serializable
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

    //view draw image variables
    private var image: Bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    private var drawBitmap: Bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    private var imagePositionRect: Rect = Rect()
    private var imageScale: Float = 1f

    //view temp variables
    private var actionsList = mutableListOf<UserEditorActions>()
    private var currentLastActionId: Int = actionsList.lastIndex
    private var oldViewSize: Pair<Int, Int> = Pair(0, 0) //Pair(width, height)
    private var startPoint: PointF = PointF(0f, 0f)
    private var endPoint: PointF = PointF(0f, 0f)

    //view state
    private var mode: EditorModeTypes = EditorModeTypes.RECTANGLE
    private var paintColor: Int = Color.RED

    private var redrawPaint: Boolean = false
    private var viewCreated: Boolean = true

    init {
        isSaveEnabled = true
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
            mode = editorMode
        }
    }

    fun setBrushColor(color: Int?) {
        if (color != null) {
            paintColor = color
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
            invalidate()
        }
    }

    fun redo() {
        if (currentLastActionId != actionsList.lastIndex) {
            currentLastActionId++
            redrawPaint = true
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
            val oldImagePosition = imagePositionRect
            setupStartParams()
            configActionsAfterRotationScreen(imagePositionRect, oldImagePosition)
            viewCreated = false
        }

        val upperLayer = onPrepareOwnCanvas()
        canvas.drawBitmap(image, null, imagePositionRect, null)
        canvas.drawBitmap(upperLayer, null, imagePositionRect, null)
        drawTempFigure(canvas)
    }

    private fun configActionsAfterRotationScreen(newRect: Rect, oldRect: Rect) {
        if (oldRect != Rect() && newRect != oldRect && actionsList.isNotEmpty()) {
            val list = actionsList.map {
                when (it) {
                    is UserEditorActions.SimpleDrawAction -> {
                        val scale: Float = newRect.width().toFloat() / oldRect.width()
                        val newStart = newPointPosition(it.startPoint, oldRect, newRect, scale)
                        val newEnd = newPointPosition(it.endPoint, oldRect, newRect, scale)

                        UserEditorActions.SimpleDrawAction(
                            newStart,
                            newEnd,
                            it.brushColor,
                            it.action
                        )
                    }
                }
            }
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
        val tempData = UserEditorActions.SimpleDrawAction(startPoint, endPoint, paintColor, mode)
        showShape(canvas, tempData)
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

            else -> {}
        }

        invalidate()
        return true
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(KEY_IMAGE_BITMAP, image)
        bundle.putParcelable(KEY_EDITED_BITMAP, drawBitmap)
        bundle.putParcelable(KEY_OLD_IMAGE_POSITION, imagePositionRect)
        bundle.putParcelableArray(KEY_ACTIONS_LIST, actionsList.toTypedArray())
        bundle.putInt(KEY_CURRENT_LIST_POSITION, currentLastActionId)
        bundle.putInt(KEY_OLD_VIEW_WIDTH, width)
        bundle.putInt(KEY_OLD_VIEW_HEIGHT, height)
        bundle.putInt(KEY_LAST_ORIENTATION, resources.configuration.orientation)
        bundle.putParcelable(SUPER_STATE_KEY, super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var viewState: Parcelable? = null
        if (state is Bundle) {
            image = state.fetchParcelable(KEY_IMAGE_BITMAP, Bitmap::class.java).orEmpty()
            drawBitmap = state.fetchParcelable(KEY_EDITED_BITMAP, Bitmap::class.java).orEmpty()

            actionsList =
                state.fetchParcelableArray(KEY_ACTIONS_LIST, UserEditorActions::class.java)
                    .orEmpty().toMutableList()
            currentLastActionId = state.getInt(KEY_CURRENT_LIST_POSITION)
            viewState = state.fetchParcelable(SUPER_STATE_KEY, Parcelable::class.java)

            imagePositionRect =
                state.fetchParcelable(KEY_OLD_IMAGE_POSITION, Rect::class.java) ?: Rect()

            oldViewSize = Pair(
                state.getInt(KEY_OLD_VIEW_WIDTH),
                state.getInt(KEY_OLD_VIEW_HEIGHT)
            )

            redrawPaint = true
            viewCreated = true
        }
        super.onRestoreInstanceState(viewState)
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
    }

    //show and update shape while user draw and dont apply result
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
            strokeWidth = 5f
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
            strokeWidth = 5f
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
            strokeWidth = 5f
            style = Paint.Style.FILL
        }

        val arrowTriangleShape = makeArrowTriangle(data.startPoint, data.endPoint)

        canvas.drawLine(
            data.startPoint.x,
            data.startPoint.y,
            data.endPoint.x,
            data.endPoint.y,
            arrowPaint
        )
        canvas.drawPath(arrowTriangleShape, arrowPaint)
    }

    private fun makeArrowTriangle(start: PointF, end: PointF): Path {
        var inRads: Double = atan2(end.y - start.y, end.x - start.x).toDouble()

        if (inRads < 0) inRads = 2 * PI - abs(inRads)

        val angle = Math.toDegrees(inRads)

        val path = Path()
        path.moveTo(end.x, end.y)

        val endX1 = (end.x - (30 * cos(Math.toRadians(angle - 30)))).toFloat()
        val endY1 = (end.y - (30 * sin(Math.toRadians(angle - 30)))).toFloat()

        val endX2 = (end.x - (30 * cos(Math.toRadians(angle + 30)))).toFloat()
        val endY2 = (end.y - (30 * sin(Math.toRadians(angle + 30)))).toFloat()

        path.lineTo(endX1, endY1)
        path.lineTo(endX2, endY2)

        path.close()

        return path
    }

    private inline fun <reified T : Parcelable> Bundle.fetchParcelableArray(
        key: String,
        clazz: Class<T>
    ): Array<out T>? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.getParcelableArray(key, clazz)
        } else {
            this.getParcelableArray(key)?.map { it as T }?.toTypedArray()
        }
    }

    private fun <T : Parcelable> Bundle.fetchParcelable(
        key: String,
        clazz: Class<T>
    ): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.getParcelable(key, clazz)
        } else {
            this.getParcelable<T>(key)
        }
    }

    private fun <T : Serializable> Bundle.fetchSerializable(
        key: String,
        clazz: Class<T>
    ): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.getSerializable(key, clazz)
        } else {
            this.getSerializable(key) as? T
        }
    }

    private fun Bitmap?.orEmpty(): Bitmap {
        return Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    }

    companion object {
        const val SUPER_STATE_KEY = "superStateKey"
        const val KEY_IMAGE_BITMAP = "keyImageBitmap"
        const val KEY_EDITED_BITMAP = "keyEditedBitmap"
        const val KEY_OLD_IMAGE_POSITION = "keyOldImagePosition"
        const val KEY_OLD_VIEW_WIDTH = "keyOldViewWidth"
        const val KEY_OLD_VIEW_HEIGHT = "keyOldViewHeight"
        const val KEY_ACTIONS_LIST = "keyActionsList"
        const val KEY_CURRENT_LIST_POSITION = "keyCurrentListPosition"
        const val KEY_LAST_ORIENTATION = "keyLastOrientation"
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
package com.onix.internship.ui.avatar.croper

import android.content.Context
import android.content.res.Configuration
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.widget.AppCompatImageView

class AvatarCropperView : AppCompatImageView {

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val mScaleDetector: ScaleGestureDetector =
        ScaleGestureDetector(context, ScaleListener())

    private var paint = Paint()
    private var viewCreated = false
    private val shaderMatrix = Matrix()
    private lateinit var shader: Shader
    private val canvasColor = Color.argb(170, 0, 0, 0)

    private var savedBitmap: Bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    private var imageScale: Float = 1.0f
    private var imagePositionRect: Rect = Rect()

    private var minImageScale: Float = 1.0f
    private var minImagePositionRect: Rect = Rect()

    fun setImage(bitmap: Bitmap) {
        savedBitmap = bitmap
        viewCreated = false
        invalidate()
    }

    fun cropImage(): Bitmap {
        val w = minImagePositionRect.right - minImagePositionRect.left
        val h = minImagePositionRect.bottom - minImagePositionRect.top

        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val startImg = imagePositionRect.left - minImagePositionRect.left
        val endImg = imagePositionRect.top - minImagePositionRect.top

        val cropRect = Rect(
            startImg,
            endImg,
            startImg + (imagePositionRect.right - imagePositionRect.left),
            endImg + (imagePositionRect.bottom - imagePositionRect.top)
        )

        Log.d("DEBUG", "Rect: $cropRect -> ($w; $h)")

        canvas.drawBitmap(
            savedBitmap,
            null,
            cropRect,
            null
        )
        return bitmap
    }

    override fun onDraw(canvas: Canvas) {
        if (!viewCreated) {
            prepareCanvas()
            setupStartParams()
            viewCreated = true
        }

        canvas.drawBitmap(
            savedBitmap,
            null,
            imagePositionRect,
            null
        )
        canvas.drawRect(0.0f, 0.0f, width.toFloat(), height.toFloat(), paint)
    }

    private fun setupStartParams() {
        imageScale = if (height >= width) {
            val scale = width.toFloat() / savedBitmap.width.toFloat()
            if (scale < minImageScale) minImageScale else scale
        } else {
            val scale = height.toFloat() / savedBitmap.height.toFloat()
            if (scale < minImageScale) minImageScale else scale
        }

        val w = (savedBitmap.width * imageScale).toInt()
        val h = (savedBitmap.height * imageScale).toInt()

        imagePositionRect = Rect(
            (width - w) / 2,
            (height - h) / 2,
            (width - w) / 2 + w,
            (height - h) / 2 + h
        )
    }

    private fun prepareCanvas() {
        val size = if (width > height) height else width
        val circlePadding = 50
        val radius = (size - circlePadding) / 2

        minImageScale = if (savedBitmap.width > savedBitmap.height) {
            (radius * 2 + 4).toFloat() / savedBitmap.height
        } else {
            (radius * 2 + 4).toFloat() / savedBitmap.width
        }

        minImagePositionRect = Rect(
            width / 2 - radius - 2,
            height / 2 - radius - 2,
            width / 2 + radius + 2,
            height / 2 + radius + 2
        )

        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val shaderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        // Draw a black rectangle.
        shaderPaint.color = canvasColor
        canvas.drawRect(
            0.0f,
            0.0f,
            size.toFloat(),
            size.toFloat(),
            shaderPaint
        )

        // Use the DST_OUT compositing mode to mask out the spotlight from the black rectangle.
        shaderPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        canvas.drawCircle(
            radius + 1f,
            radius + 1f,
            radius.toFloat(),
            shaderPaint
        )

        shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        shaderMatrix.setTranslate(
            (width / 2 - radius).toFloat(),
            (height / 2 - radius).toFloat()
        )
        shader.setLocalMatrix(shaderMatrix)

        paint.shader = shader
    }

    private var lastTouchX: Float = 0.0f
    private var lastTouchY: Float = 0.0f

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        when (motionEvent.pointerCount) {
            1 -> checkEventFinish(motionEvent) {
                moveImage(motionEvent)
            }
            2 -> checkEventFinish(motionEvent) {
                mScaleDetector.onTouchEvent(motionEvent)
            }
            else -> {}
        }

        invalidate()
        return true
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        viewCreated = false
        invalidate()
    }

    private fun checkEventFinish(motionEvent: MotionEvent, action: () -> Unit) {
        when (motionEvent.action) {
            MotionEvent.ACTION_UP -> {
                lastTouchX = 0f
                lastTouchY = 0f
                checkCorrectScaleAndPosition()
            }
            else -> {
                action.invoke()
            }
        }
    }

    private fun moveImage(motionEvent: MotionEvent) {
        when (motionEvent.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = motionEvent.x
                lastTouchY = motionEvent.y
            }
            MotionEvent.ACTION_MOVE -> {
                val beforeX: Float = lastTouchX
                val beforeY: Float = lastTouchY
                lastTouchX = motionEvent.x
                lastTouchY = motionEvent.y

                val delX = (lastTouchX - beforeX).toInt()
                val delY = (lastTouchY - beforeY).toInt()

                imagePositionRect = Rect(
                    imagePositionRect.left + delX,
                    imagePositionRect.top + delY,
                    imagePositionRect.right + delX,
                    imagePositionRect.bottom + delY
                )
            }
        }
    }

    private fun checkCorrectScaleAndPosition() {
        if (imageScale < minImageScale) {
            imageScale = minImageScale
            scaleImage()
        }

        if (minImagePositionRect.left < imagePositionRect.left) {
            val diff = imagePositionRect.left - minImagePositionRect.left
            imagePositionRect.left -= diff
            imagePositionRect.right -= diff
        }
        if (minImagePositionRect.top < imagePositionRect.top) {
            val diff = imagePositionRect.top - minImagePositionRect.top
            imagePositionRect.top -= diff
            imagePositionRect.bottom -= diff
        }
        if (minImagePositionRect.right > imagePositionRect.right) {
            val diff = minImagePositionRect.right - imagePositionRect.right
            imagePositionRect.left += diff
            imagePositionRect.right += diff
        }
        if (minImagePositionRect.bottom > imagePositionRect.bottom) {
            val diff = minImagePositionRect.bottom - imagePositionRect.bottom
            imagePositionRect.top += diff
            imagePositionRect.bottom += diff
        }
    }

    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val mScaleFactor = detector.scaleFactor
            imageScale *= mScaleFactor
            scaleImage()
            return true
        }
    }

    private fun scaleImage() {
        val w = imagePositionRect.right - imagePositionRect.left
        val h = imagePositionRect.bottom - imagePositionRect.top

        val diffW = ((savedBitmap.width * imageScale).toInt() - w) / 2
        val diffH = ((savedBitmap.height * imageScale).toInt() - h) / 2
        imagePositionRect = Rect(
            imagePositionRect.left - diffW,
            imagePositionRect.top - diffH,
            imagePositionRect.right + diffW,
            imagePositionRect.bottom + diffH
        )
    }
}
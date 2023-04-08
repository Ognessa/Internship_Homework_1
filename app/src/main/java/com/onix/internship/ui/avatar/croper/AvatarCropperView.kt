package com.onix.internship.ui.avatar.croper

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap
import com.onix.internship.R

class AvatarCropperView : AppCompatImageView {

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var paint = Paint()
    private var viewCreated = false
    private val shaderMatrix = Matrix()
    private var shader: Shader

    private val spotlight = BitmapFactory.decodeResource(resources, R.drawable.mask)
    val radius = spotlight.width / 2

    private var savedBitmap: Bitmap =
        Bitmap.createBitmap(spotlight.width, spotlight.height, Bitmap.Config.ARGB_8888)
    private var imageScale: Float = 1.0f
    private var imagePositionRect: Rect = Rect()

    init {
        val bitmap = Bitmap.createBitmap(spotlight.width, spotlight.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val shaderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        // Draw a black rectangle.
        shaderPaint.color = Color.argb(170, 0, 0, 0)
        canvas.drawRect(
            0.0f,
            0.0f,
            spotlight.width.toFloat(),
            spotlight.height.toFloat(),
            shaderPaint
        )

        // Use the DST_OUT compositing mode to mask out the spotlight from the black rectangle.
        shaderPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        canvas.drawBitmap(spotlight, 0.0f, 0.0f, shaderPaint)

        shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
    }

    fun setImage(@DrawableRes resId: Int) {
        //super.setImageResource(resId)
        savedBitmap = resources.getDrawable(resId, null).toBitmap()

        invalidate()
        Log.d("DEBUG", "set")
    }

    override fun onDraw(canvas: Canvas) {
        Log.d("DEBUG", "redraw")
//        if (!viewCreated) {
//            super.onDraw(canvas)
//            shaderMatrix.setTranslate(
//                (width / 2 - radius).toFloat(),
//                (height / 2 - radius).toFloat()
//            )
//            shader.setLocalMatrix(shaderMatrix)
//        viewCreated = true
//        } else {
//        }
//        canvas.drawRect(0.0f, 0.0f, width.toFloat(), height.toFloat(), paint)

        if (!viewCreated) {
            setupStartParams()
            viewCreated = true
        }

        canvas.drawBitmap(
            savedBitmap,
            null,
            imagePositionRect,
            null
        )
    }

    private fun setupStartParams() {
        imageScale = if ((height / savedBitmap.height) >= (width / savedBitmap.width)) {
            width.toFloat() / savedBitmap.width.toFloat()
        } else {
            height.toFloat() / savedBitmap.height.toFloat()
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

    override fun onSizeChanged(
        newWidth: Int,
        newHeight: Int,
        oldWidth: Int,
        oldHeight: Int
    ) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight)
//        setupWinnerRect()
    }

    private var lastTouchX: Float = 0.0f
    private var lastTouchY: Float = 0.0f

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {

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

                invalidate()
            }
        }

//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                shouldDrawSpotLight = true
//            }
//            MotionEvent.ACTION_UP -> {
//                shouldDrawSpotLight = true
//            }
//        }
//        shaderMatrix.setTranslate(
//            motionEventX - spotlight.width / 2.0f,
//            motionEventY - spotlight.height / 2.0f
//        )
//        shader.setLocalMatrix(shaderMatrix)
//        invalidate()
        return true
    }

}
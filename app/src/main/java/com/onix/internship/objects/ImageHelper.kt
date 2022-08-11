package com.onix.internship.objects

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap

class ImageHelper {
    fun createFilteredImage(imageView: ImageView): Bitmap {
        val original = imageView.drawable.toBitmap()
        val bitmap = Bitmap.createBitmap(
            original.width,
            original.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)

        val paint = Paint()
        paint.colorFilter = imageView.colorFilter

        canvas.drawBitmap(original, 0f, 0f, paint)

        return bitmap
    }
}
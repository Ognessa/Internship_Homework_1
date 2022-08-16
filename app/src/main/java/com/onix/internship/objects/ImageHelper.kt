package com.onix.internship.objects

import android.app.Activity
import android.graphics.*
import android.os.Build
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStoragePublicDirectory
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import android.widget.ImageView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageHelper(
    private val galleryInjector : DeviceGalleryInjector
    ) {

    fun createFilteredImage(view: ImageView, activity: Activity): Bitmap {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val location = IntArray(2)
            view.getLocationInWindow(location)
            PixelCopy.request(
                activity.window,
                Rect(location[0], location[1], location[0] + view.width, location[1] + view.height),
                bitmap,
                {
                    if (it == PixelCopy.SUCCESS) {
                        val canvas = Canvas(bitmap)

                        val paint = Paint()
                        paint.colorFilter = view.colorFilter

                        canvas.drawBitmap(bitmap, 0f, 0f, paint)
                    }
                },
                Handler(Looper.getMainLooper())
            )
            return bitmap
        } else {
            val tBitmap = Bitmap.createBitmap(
                view.width, view.height, Bitmap.Config.RGB_565
            )
            val canvas = Canvas(tBitmap)

            val paint = Paint()
            paint.colorFilter = view.colorFilter
            canvas.drawBitmap(tBitmap, 0f, 0f, paint)
            return tBitmap
        }
    }

    fun saveImage(bitmap: Bitmap) {
        val fileExported =
            File(getExternalStoragePublicDirectory(DIRECTORY_PICTURES).path, "Image.png")
        try {
            FileOutputStream(fileExported).use { out ->
                bitmap
                    .compress(Bitmap.CompressFormat.PNG, 100, out)
            }
            galleryInjector.saveMediaIntoGallery(fileExported)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        fileExported.delete()
    }
}
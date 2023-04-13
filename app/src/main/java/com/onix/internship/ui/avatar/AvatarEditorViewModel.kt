package com.onix.internship.ui.avatar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.onix.internship.arch.controller.BaseViewModel
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class AvatarEditorViewModel(
    private val context: Context
) : BaseViewModel() {

    private val _image = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap> = _image

    fun loadBitmap(url: String) {
        launch {
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(url)
                .allowHardware(false) // Disable hardware bitmaps.
                .build()

            val result = (loader.execute(request) as SuccessResult).drawable
            _image.value = (result as BitmapDrawable).bitmap
        }
    }

    fun saveBitmap(bitmap: Bitmap): String? {
        val dir = File(context.filesDir, "temp")
        if (!dir.exists()) {
            dir.mkdir()
        }
        val file = File(dir, "cropped_${System.currentTimeMillis()}.png")
        return try {
            val fos = file.outputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos)
            fos.close()
            file.path
        } catch (e: FileNotFoundException) {
            Log.d("DEBUG", "File not found: " + e.message)
            null
        } catch (e: IOException) {
            Log.d("DEBUG", "Error accessing file: " + e.message)
            null
        }
    }


}
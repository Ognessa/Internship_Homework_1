package com.onix.internship.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import com.onix.internship.R
import java.util.*

class ImageRepository(val context: Context) {
    private val list = mutableListOf<Bitmap>()

    private val minListSize = 1
    private val maxListSize = 2

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getImage(): Bitmap {
        return if (list.isNotEmpty())
            list.last()
        else
            context.getDrawable(R.drawable.ic_empty_image)!!.toBitmap()
    }

    fun restoreImage(): Bitmap {
        return if (list.size != minListSize){
            list.removeLast()
            list.last()
        } else{
            list.last()
        }
    }

    fun saveAllChanges(){
        val lastImage = list.last()
        list.apply {
            clear()
            add(lastImage)
        }
    }

    fun saveImage(bitmap: Bitmap) {
        list.add(bitmap)

        if(list.size > maxListSize)
            list.removeFirst()
    }
}
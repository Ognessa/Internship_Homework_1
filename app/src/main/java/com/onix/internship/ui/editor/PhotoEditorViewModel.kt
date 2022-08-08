package com.onix.internship.ui.editor

import android.graphics.Bitmap
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.data.repository.ImageRepository

class PhotoEditorViewModel(private val imageRepository: ImageRepository) : BaseViewModel(){

    fun getImage(): Bitmap {
        return imageRepository.imageBitmap
    }
}
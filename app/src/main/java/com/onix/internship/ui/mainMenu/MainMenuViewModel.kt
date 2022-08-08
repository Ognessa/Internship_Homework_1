package com.onix.internship.ui.mainMenu

import android.graphics.Bitmap
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.data.repository.ImageRepository
import kotlin.random.Random

class MainMenuViewModel(val imageRepository: ImageRepository) : BaseViewModel(){

    val galleryImport = SingleLiveEvent<Unit>()
    val cameraImport = SingleLiveEvent<Unit>()

    fun galleryImport(){
        galleryImport.postValue(Unit)
    }

    fun cameraImport(){
        cameraImport.postValue(Unit)
    }

    fun saveImage(img : Bitmap){
        imageRepository.imageBitmap = img
    }
}
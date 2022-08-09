package com.onix.internship.ui.mainMenu

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent

class MainMenuViewModel : BaseViewModel(){

    val galleryImport = SingleLiveEvent<Unit>()
    val cameraImport = SingleLiveEvent<Unit>()

    fun galleryImport(){
        galleryImport.postValue(Unit)
    }

    fun cameraImport(){
        cameraImport.postValue(Unit)
    }
}
package com.onix.internship.ui.colorEditor

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ColorFilter
import android.graphics.LightingColorFilter
import android.widget.ImageView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.data.repository.ImageRepository
import com.onix.internship.objects.ImageHelper

class ColorEditorViewModel(
    private val imageRepository: ImageRepository,
    private val imageHelper: ImageHelper
) : BaseViewModel(){

    val saveEvent = SingleLiveEvent<Unit>()

    private val _restoreEvent = MutableLiveData<Unit>()
    val restoreEvent : LiveData<Unit> get() = _restoreEvent

    fun getImage(): Bitmap {
        return imageRepository.getImage()
    }

    fun saveFilteredImage(view: ImageView, activity: Activity){
        imageRepository.saveImage(
            imageHelper.createFilteredImage(view, activity)
        )
        imageRepository.saveAllChanges()
    }

    fun saveImageToMemory(){
        imageHelper.saveImage(imageRepository.getImage())
    }

    fun onClickSave() {
        saveEvent.value = Unit
    }

    fun onClickRestore() {
        _restoreEvent.value = Unit
    }
}
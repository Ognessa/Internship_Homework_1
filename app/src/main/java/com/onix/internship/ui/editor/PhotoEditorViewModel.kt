package com.onix.internship.ui.editor

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ColorFilter
import androidx.constraintlayout.utils.widget.ImageFilterView
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.data.repository.ImageRepository
import com.onix.internship.objects.FilterData
import com.onix.internship.objects.ImageHelper

class PhotoEditorViewModel(
    private val imageRepository: ImageRepository,
    private val imageHelper: ImageHelper
) : BaseViewModel(){

    val model = PhotoEditorModel()

    private val minListSize = 1
    private val maxListSize = 2

    fun getFilterData(): FilterData {
        return model.filterHistory.last().copy()
    }

    fun restoreFilter(): FilterData {
        return if (model.filterHistory.size != minListSize){
            model.filterHistory.removeLast()
            model.filterHistory.last().copy()
        } else{
            model.filterHistory.last().copy()
        }
    }

    fun saveFilterData(data: FilterData){
        model.filterHistory.add(data)

        if (model.filterHistory.size > maxListSize)
            model.filterHistory.removeFirst()
    }

    fun getImage(): Bitmap {
        return imageRepository.getImage()
    }

    fun saveFilteredImage(view: ImageFilterView, activity: Activity){
        imageRepository.saveImage(
            imageHelper.createFilteredImage(view, activity)
        )
        imageRepository.saveAllChanges()
    }

    fun onClickSave() {
        model.saveAndNavigateEvent.value = Unit
    }

    fun onClickRestore() {
        model.mutableRestoreEvent.value = Unit
    }
}
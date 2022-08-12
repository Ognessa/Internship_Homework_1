package com.onix.internship.ui.crop

import android.graphics.Bitmap
import com.onix.internship.R
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.data.repository.ImageRepository

class CropViewModel(
    private val imageRepository : ImageRepository
    ) : BaseViewModel(){

   val model = CropModel()

    fun onClickRatio(width: Int, height: Int) {
        if (isPossibleCrop(width, height, imageRepository.getImage())) {
            model.ratioEvent.set(Pair(width, height))
        } else {
            showSnack(imageRepository.context.resources.getString(R.string.can_not_crop))
        }
    }

    fun onClickCrop() {
        model.cropEvent.value = Unit
    }

    fun onClickSave() {
        model.saveAndNavigateEvent.value = Unit
    }

    fun onClickRestore() {
        model.restoreEvent.value = Unit
    }

    fun getImage(): Bitmap {
        return imageRepository.getImage()
    }

    fun saveImage(bitmap: Bitmap){
        imageRepository.saveImage(bitmap)
    }

    fun saveResult(bitmap: Bitmap){
        imageRepository.saveImage(bitmap)
        imageRepository.saveAllChanges()
    }

    fun restoreImage(): Bitmap {
        return imageRepository.restoreImage()
    }

    private fun isPossibleCrop(widthRatio: Int, heightRatio: Int, bitmap : Bitmap): Boolean {
        bitmap.let {
            val width = it.width
            val height = it.height
            return !(width < widthRatio && height < heightRatio)
        }
    }
}
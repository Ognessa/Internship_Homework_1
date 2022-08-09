package com.onix.internship.ui.crop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent

class CropViewModel : BaseViewModel(){

    private val _ratioEvent = MutableLiveData<Pair<Int, Int>>()
    val ratioEvent: LiveData<Pair<Int, Int>> = _ratioEvent

    val cropEvent = SingleLiveEvent<Unit>()
    val saveEvent = SingleLiveEvent<Unit>()
    val restoreEvent = SingleLiveEvent<Unit>()

    fun onClickRatio(width: Int, height: Int) {
        _ratioEvent.value = Pair(width, height)
    }

    fun onClickCrop() {
        cropEvent.value = Unit
    }

    fun onClickSave() {
        saveEvent.value = Unit
    }

    fun onClickRestore() {
        restoreEvent.value = Unit
    }
}
package com.onix.internship.ui.colorEditor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent

class ColorEditorViewModel : BaseViewModel(){

    val saveEvent = SingleLiveEvent<Unit>()

    private val _restoreEvent = MutableLiveData<Unit>()
    val restoreEvent : LiveData<Unit> get() = _restoreEvent

    fun onClickSave() {
        saveEvent.value = Unit
    }

    fun onClickRestore() {
        _restoreEvent.value = Unit
    }
}
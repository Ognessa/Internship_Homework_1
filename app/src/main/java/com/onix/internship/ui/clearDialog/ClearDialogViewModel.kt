package com.onix.internship.ui.clearDialog

import androidx.lifecycle.LiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent

class ClearDialogViewModel(private val model: ClearModel) : BaseViewModel(){
    private val _moveBack = SingleLiveEvent<Unit>()
    val moveBack: LiveData<Unit> = _moveBack

    fun clearDB(){
        model.clear.set(true)
        _moveBack.postValue(Unit)
    }

    fun cancel(){
        _moveBack.postValue(Unit)
    }
}
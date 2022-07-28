package com.onix.internship.ui.mainFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent

class MainMenuViewModel : BaseViewModel(){
    private val _addNoteBtnVisibility = MutableLiveData(true)
    val addNoteBtnVisibility : LiveData<Boolean> get() = _addNoteBtnVisibility

    val navigateToAddNote = SingleLiveEvent<Boolean>()

    fun setAddNoteBtnVisible(boolean: Boolean){
        _addNoteBtnVisibility.postValue(boolean)
    }

    fun navigate(){
        navigateToAddNote.value = true
    }
}
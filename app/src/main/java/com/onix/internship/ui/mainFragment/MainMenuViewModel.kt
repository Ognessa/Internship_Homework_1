package com.onix.internship.ui.mainFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent

class MainMenuViewModel : BaseViewModel(){
    private val _addNoteBtnVisibility = MutableLiveData(true)
    val addNoteBtnVisibility : LiveData<Boolean> get() = _addNoteBtnVisibility

    val navigateToAddNote = SingleLiveEvent<Boolean>()
    val navigateToEditNote = SingleLiveEvent<Int>()

    fun setAddNoteBtnVisible(boolean: Boolean){
        _addNoteBtnVisibility.postValue(boolean)
    }

    fun navigateToEditNote(itemId : Int){
        navigateToEditNote.value = itemId
    }

    fun navigate(){
        navigateToAddNote.value = true
    }
}
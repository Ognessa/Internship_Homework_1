package com.onix.internship.ui.askClass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.data.keys.Keys
import com.onix.internship.data.repository.PreferenceStorage
import com.onix.internship.objects.NavigateDirection

class AskClassViewModel(private val pref : PreferenceStorage) : BaseViewModel(){
    val move = SingleLiveEvent<NavigateDirection>()

    private val _selectedClass = MutableLiveData(false)
    val selectedClass : LiveData<Boolean> get() = _selectedClass

    fun selectClass(classId : Int){
        pref.save(Keys().classKey, classId.toString())
        _selectedClass.postValue(true)
    }

    fun navigateTo(direction : NavigateDirection){
        move.postValue(direction)
    }
}
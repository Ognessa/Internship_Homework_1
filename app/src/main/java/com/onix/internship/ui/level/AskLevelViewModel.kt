package com.onix.internship.ui.level

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.data.keys.Keys
import com.onix.internship.data.repository.PreferenceStorage

class AskLevelViewModel(private val pref : PreferenceStorage) : BaseViewModel(){

    private val _level = MutableLiveData<Int>()
    val level : LiveData<Int> get() = _level

    fun updateLevel(value : Int){
        _level.postValue(value)
        pref.save(Keys().levelKey, value.toString())
    }

}
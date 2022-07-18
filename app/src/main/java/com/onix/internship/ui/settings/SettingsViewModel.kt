package com.onix.internship.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.data.keys.Keys
import com.onix.internship.data.repository.PreferenceStorage

class SettingsViewModel(private val pref : PreferenceStorage) : BaseViewModel(){
    private val _level = MutableLiveData(pref.getString(Keys().levelKey).toString().toInt())
    val level : LiveData<Int> get() = _level

    private val _pointClass = MutableLiveData(pref.getString(Keys().classKey).toString().toInt())
    val pointClass : LiveData<Int> get() = _pointClass

    fun updateLevel(value : Int){
        _level.postValue(value)
        pref.save(Keys().levelKey, value.toString())
    }

    fun updateClass(pointClass : Int){
        _pointClass.postValue(pointClass)
        pref.save(Keys().classKey, pointClass.toString())
    }
}
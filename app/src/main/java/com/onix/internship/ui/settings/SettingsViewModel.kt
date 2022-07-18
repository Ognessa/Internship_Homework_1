package com.onix.internship.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.data.keys.Keys
import com.onix.internship.data.repository.PreferenceStorage

class SettingsViewModel(private val pref : PreferenceStorage) : BaseViewModel(){
    private val _level = MutableLiveData<Int>()
    val level : LiveData<Int> get() = _level

    private val _pointClass = MutableLiveData<Int>()
    val pointClass : LiveData<Int> get() = _pointClass

    init{
        _level.postValue(pref.getString(Keys().levelKey)?.toInt())
        _pointClass.postValue(pref.getString(Keys().classKey)?.toInt() ?: 0)
    }

    fun updateLevel(value : Int){
        _level.postValue(value)
        pref.save(Keys().levelKey, level.toString())
    }

    fun updateClass(pointClass : Int){
        _pointClass.postValue(pointClass)
        pref.save(Keys().classKey, pointClass.toString())
    }
}
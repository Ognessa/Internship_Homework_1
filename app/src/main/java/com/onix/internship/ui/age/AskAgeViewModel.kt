package com.onix.internship.ui.age

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel

class AskAgeViewModel : BaseViewModel(){
    private val _is18YearsOld = MutableLiveData(false)
    val is18YearsOld : LiveData<Boolean> get() = _is18YearsOld

    fun updateIs18YOValue(){
        _is18YearsOld.postValue(!_is18YearsOld.value!!)
    }
}

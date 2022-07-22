package com.onix.internship.ui.age

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent

class AskAgeViewModel : BaseViewModel(){
    val moveNext = SingleLiveEvent<Boolean>()

    private val _is16YearsOld = MutableLiveData(false)
    val is16YearsOld : LiveData<Boolean> get() = _is16YearsOld

    fun updateIs16YOValue(){
        _is16YearsOld.postValue(!_is16YearsOld.value!!)
    }

    fun navigateNext(){
        moveNext.postValue(true)
    }
}

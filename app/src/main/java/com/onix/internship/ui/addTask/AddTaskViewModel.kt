package com.onix.internship.ui.addTask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.objects.Tag

class AddTaskViewModel : BaseViewModel(){
    private val _selectedDate = MutableLiveData<String>()
    val selectedDate : LiveData<String> get() = _selectedDate

    private val _tagList = MutableLiveData<ArrayList<Tag>>()
    val tagList : LiveData<ArrayList<Tag>> get() = _tagList

    private val _timeFrom = MutableLiveData<String>()
    val timeFrom : LiveData<String> get() = _timeFrom

    private val _timeUntil = MutableLiveData<String>()
    val timeUntil : LiveData<String> get() = _timeUntil

    init {
        _timeFrom.postValue("00:00")
        _timeUntil.postValue("00:00")
    }

    fun updateTimeFrom(hours : Int, minutes : Int){
        _timeFrom.postValue(formatTime(hours, minutes))
    }

    fun updateTimeUntil(hours : Int, minutes : Int){
        _timeUntil.postValue(formatTime(hours, minutes))
    }

    fun addNewTag(tag : Tag){
        if(!_tagList.value.isNullOrEmpty()) {
            val list = _tagList.value
            list!!.add(tag)
            _tagList.postValue(list)
        }
        else{
            val list = arrayListOf(tag)
            _tagList.postValue(list)
        }
    }

    fun setSelectedCalendarDate(date : String){
        _selectedDate.postValue(date)
    }

    fun getSelectedCalendarDate(year : Int, mouth : Int, day : Int) : String{
        val mouths = arrayListOf("January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December")
        return "$day ${mouths[mouth]} $year"
    }

    private fun formatTime(hours : Int, minutes : Int): String {
        return when(true){
            (hours < 10 && minutes < 10) -> "0$hours:0$minutes"
            (hours < 10 && minutes > 10) -> "0$hours:$minutes"
            (hours > 10 && minutes < 10) -> "$hours:0$minutes"
            else -> "$hours:$minutes"
        }
    }
}
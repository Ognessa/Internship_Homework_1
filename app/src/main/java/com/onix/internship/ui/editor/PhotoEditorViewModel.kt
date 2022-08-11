package com.onix.internship.ui.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.objects.FilterData

class PhotoEditorViewModel : BaseViewModel(){

    private val filterHistory = mutableListOf(FilterData())
    private val minListSize = 1
    private val maxListSize = 2

    val saveEvent = SingleLiveEvent<Unit>()

    private val _restoreEvent = MutableLiveData<Unit>()
    val restoreEvent : LiveData<Unit> get() = _restoreEvent

    fun getFilterData(): FilterData {
        return filterHistory.last().copy()
    }

    fun restoreFilter(): FilterData {
        return if (filterHistory.size != minListSize){
            filterHistory.removeLast()
            filterHistory.last().copy()
        } else{
            filterHistory.last().copy()
        }
    }

    fun saveFilterData(data: FilterData){
        filterHistory.add(data)
        if (filterHistory.size > maxListSize) filterHistory.removeFirst()
    }

    fun onClickSave() {
        saveEvent.value = Unit
    }

    fun onClickRestore() {
        _restoreEvent.value = Unit
    }
}
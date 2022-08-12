package com.onix.internship.ui.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.objects.FilterData

class PhotoEditorModel {
    val filterHistory = mutableListOf(FilterData())

    val saveAndNavigateEvent = SingleLiveEvent<Unit>()

    val mutableRestoreEvent = MutableLiveData<Unit>()
    val restoreEvent : LiveData<Unit> get() = mutableRestoreEvent
}
package com.onix.internship.ui.notesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.objects.DataStore
import com.onix.internship.objects.NotesData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotesListViewModel(private val dataStore: DataStore) : BaseViewModel() {

    private val _notesUpdated = MutableLiveData<List<NotesData>>(arrayListOf())
    val notesUpdated: LiveData<List<NotesData>> get() = _notesUpdated

    fun showData() {
        launch {
            withContext(Dispatchers.IO) {
                _notesUpdated.postValue(dataStore.gelAllData())
            }
        }
    }

}
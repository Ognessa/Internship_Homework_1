package com.onix.internship.ui.addNote

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.R
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.objects.AddNoteFragmentStates
import com.onix.internship.objects.DataStore
import com.onix.internship.objects.NotesColors
import com.onix.internship.objects.NotesData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateTextModel(val title: String, val description: String)

class AddNoteViewModel(private val dataStore: DataStore, private val application: Application) :
    BaseViewModel() {

    private val _note = MutableLiveData<NotesData>()
    val note: LiveData<NotesData> get() = _note

    val updateText = SingleLiveEvent<UpdateTextModel>()

    private val _saveNoteEvent = MutableLiveData<Boolean>()
    val saveNoteEvent: LiveData<Boolean> get() = _saveNoteEvent

    private val _returnToMenu = MutableLiveData<Boolean>()
    val returnToMenu : LiveData<Boolean> get() = _returnToMenu

    fun initValue(addNoteFragmentStates: AddNoteFragmentStates, itemId: Int) {
        when (addNoteFragmentStates) {
            AddNoteFragmentStates.ADD_NEW_NOTE -> {
                _note.postValue(NotesData(0, "", "", NotesColors.RED, true))
            }
            AddNoteFragmentStates.EDIT_OLD_NOTE -> {
                launch {
                    withContext(Dispatchers.IO) {
                        val notesData = dataStore.getById(itemId)
                        _note.postValue(notesData)
                        updateText.postValue(
                            UpdateTextModel(
                                notesData.title,
                                notesData.description
                            )
                        )
                    }
                }
            }
        }
    }

    fun updateTitle(title: String) {
        val value = _note.value
        value?.title = title
        _note.postValue(value)
    }

    fun updateDescription(description: String) {
        val value = _note.value
        value?.description = description
        _note.postValue(value)
    }

    fun updateColor(color: NotesColors) {
        val value = _note.value
        value?.color = color
        _note.postValue(value)
    }

    fun updateEditAble(boolean: Boolean) {
        val value = _note.value
        value?.isEditable = boolean
        _note.postValue(value)
    }

    fun saveNoteEvent() {
        _saveNoteEvent.postValue(true)
    }

    fun saveNote(addNoteFragmentStates: AddNoteFragmentStates) {
        val value = _note.value
        if (!value?.title.isNullOrEmpty() && !value?.description.isNullOrEmpty()) {
            launch {
                withContext(Dispatchers.IO) {
                    when (addNoteFragmentStates) {
                        AddNoteFragmentStates.ADD_NEW_NOTE -> note.value?.let {
                            dataStore.addNewNote(it)
                        }
                        AddNoteFragmentStates.EDIT_OLD_NOTE -> note.value?.let {
                            dataStore.editNote(it)
                        }
                    }
                    _returnToMenu.postValue(true)
                }
            }
        } else {
            showMsgError(application.resources.getString(R.string.saving_note_error))
        }
    }

}
package com.onix.internship.di

import com.onix.internship.ui.addNote.AddNoteViewModel
import com.onix.internship.ui.emergency.EmergencyViewModel
import com.onix.internship.ui.help.HelpViewModel
import com.onix.internship.ui.main.MainViewModel
import com.onix.internship.ui.mainFragment.MainMenuViewModel
import com.onix.internship.ui.notesList.NotesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { NotesListViewModel(get()) }
    viewModel { EmergencyViewModel(get()) }
    viewModel { HelpViewModel() }
    viewModel { AddNoteViewModel(get(), get()) }
    viewModel { MainMenuViewModel() }
}
package com.onix.internship.di

import com.onix.internship.ui.crop.CropViewModel
import com.onix.internship.ui.editor.PhotoEditorViewModel
import com.onix.internship.ui.main.MainViewModel
import com.onix.internship.ui.mainMenu.MainMenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { MainMenuViewModel() }
    viewModel { CropViewModel() }
    viewModel { PhotoEditorViewModel() }
}
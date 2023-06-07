package com.onix.internship.di

import com.onix.internship.ui.editor.EditorViewModel
import com.onix.internship.ui.editor.colors.ColorPickerViewModel
import com.onix.internship.ui.editor.data.EditorManager
import com.onix.internship.ui.gallery.GalleryViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel {
        GalleryViewModel(
            editorManager = get<EditorManager>()
        )
    }

    viewModel {
        EditorViewModel(
            context = androidApplication(),
            editorManager = get<EditorManager>()
        )
    }

    viewModel {
        ColorPickerViewModel(
            selectedColor = get<Int>()
        )
    }
}
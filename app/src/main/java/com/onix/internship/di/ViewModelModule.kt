package com.onix.internship.di

import com.onix.internship.ui.gallery.GalleryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { GalleryViewModel() }
}
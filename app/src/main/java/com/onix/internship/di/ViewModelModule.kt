package com.onix.internship.di

import com.onix.internship.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { HomeViewModel(get()) }
}
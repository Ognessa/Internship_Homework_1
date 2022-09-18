package com.onix.internship.di

import com.onix.internship.ui.list.ListViewModel
import com.onix.internship.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ListViewModel() }
    viewModel { MainViewModel() }
}
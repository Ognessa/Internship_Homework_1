package com.onix.internship.di

import com.onix.internship.ui.main.MainViewModel
import com.onix.internship.ui.player.PlayerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PlayerViewModel() }
    viewModel { MainViewModel() }
}
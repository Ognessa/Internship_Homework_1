package com.onix.internship.di

import com.onix.internship.ui.game.GameViewModel
import com.onix.internship.ui.main.MainViewModel
import com.onix.internship.ui.mainmenu.MainMenuViewModel
import com.onix.internship.ui.pause.PauseViewModel
import com.onix.internship.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { MainViewModel() }
    viewModel { MainMenuViewModel() }
    viewModel { GameViewModel(get()) }
    viewModel { PauseViewModel(get()) }
}
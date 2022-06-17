package com.onix.internship.di

import com.onix.internship.ui.main.MainViewModel
import com.onix.internship.ui.menu.GameMenuViewModel
import com.onix.internship.ui.splash.SplashViewModel
import com.onix.internship.ui.tictactoe.TicTacToeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { MainViewModel() }
    viewModel { TicTacToeViewModel() }
    viewModel { GameMenuViewModel() }
    //TODO add viewModels
}
package com.onix.internship.di

import com.onix.internship.ui.main.MainViewModel
import com.onix.internship.ui.moreinfo.MoreInfoViewModel
import com.onix.internship.ui.splash.SplashViewModel
import com.onix.internship.ui.wifi.WifiViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { MainViewModel() }
    viewModel { WifiViewModel(appWifiManager = get()) }
    viewModel { MoreInfoViewModel() }
}
package com.onix.internship.di

import com.onix.internship.ui.age.AskAgeViewModel
import com.onix.internship.ui.askClass.AskClassViewModel
import com.onix.internship.ui.level.AskLevelViewModel
import com.onix.internship.ui.locationPermission.LocationPermissionViewModel
import com.onix.internship.ui.main.MainViewModel
import com.onix.internship.ui.map.MapViewModel
import com.onix.internship.ui.points.PointsViewModel
import com.onix.internship.ui.settings.SettingsViewModel
import com.onix.internship.ui.tabMenu.TabMenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }

    viewModel { AskAgeViewModel() }
    viewModel { AskLevelViewModel(get()) }
    viewModel { AskClassViewModel(get()) }

    viewModel { LocationPermissionViewModel(get()) }

    viewModel { TabMenuViewModel() }
    viewModel { MapViewModel(get(), get()) }
    viewModel { PointsViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}
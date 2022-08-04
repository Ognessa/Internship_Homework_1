package com.onix.internship.di

import com.onix.internship.ui.addSensor.AddSensorViewModel
import com.onix.internship.ui.main.MainViewModel
import com.onix.internship.ui.sensors.SensorsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { SensorsListViewModel(get(), get()) }
    viewModel { AddSensorViewModel(get(), get()) }
}
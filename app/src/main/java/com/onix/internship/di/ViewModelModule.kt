package com.onix.internship.di

import com.onix.internship.ui.addDevice.AddDeviceViewModel
import com.onix.internship.ui.deviceList.DeviceListViewModel
import com.onix.internship.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DeviceListViewModel(get()) }
    viewModel { AddDeviceViewModel(get()) }
    viewModel { MainViewModel() }
}
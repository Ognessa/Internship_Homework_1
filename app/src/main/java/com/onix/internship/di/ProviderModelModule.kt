package com.onix.internship.di

import com.onix.internship.data.storage.SensorProvider
import com.onix.internship.data.storage.SensorStorage
import com.onix.internship.ui.sensors.SensorsListModel
import org.koin.dsl.module

val providerModelModule = module {
    single { SensorStorage() }
    single { SensorProvider() }
    single { SensorsListModel() }
}
package com.onix.internship.di

import com.onix.internship.arch.provider.TextResProvider
import com.onix.internship.ui.wifi.AppWifiManager
import com.onix.internship.ui.wifi.FakeWifiManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val providerModule = module {
    single { TextResProvider(get()) }
    single { AppWifiManager(context = androidApplication()) }
    single { FakeWifiManager(context = androidApplication()) }
}
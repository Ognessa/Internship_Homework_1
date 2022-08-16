package com.onix.internship.di

import com.onix.internship.arch.provider.TextResProvider
import com.onix.internship.objects.ImageHelper
import com.onix.internship.objects.DeviceGalleryInjector
import org.koin.dsl.module

val providerModule = module {
    single { TextResProvider(get()) }
    single { ImageHelper(get()) }
    single { DeviceGalleryInjector(get()) }
}
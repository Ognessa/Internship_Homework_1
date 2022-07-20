package com.onix.internship.di

import com.onix.internship.arch.provider.TextResProvider
import com.onix.internship.objects.PointsStore
import org.koin.dsl.module

val providerModule = module {
    single { TextResProvider(get()) }
    single { PointsStore() }
}
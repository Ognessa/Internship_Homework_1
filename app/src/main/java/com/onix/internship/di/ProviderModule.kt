package com.onix.internship.di

import com.onix.internship.arch.provider.TextResProvider
import org.koin.dsl.module

val providerModule = module {
    single { TextResProvider(get()) }
}
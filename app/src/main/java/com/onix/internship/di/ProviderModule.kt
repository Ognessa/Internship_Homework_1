package com.onix.internship.di

import com.onix.internship.arch.provider.TextResProvider
import com.onix.internship.retrofit.NetworkFactory
import com.onix.internship.retrofit.NetworkService
import org.koin.dsl.module

val providerModule = module {
    single { TextResProvider(get()) }
    single { NetworkFactory() }
}
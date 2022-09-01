package com.onix.internship.di

import com.onix.internship.network.*
import org.koin.dsl.module

val networkModule = module {
    single { Network(get(), get()) }
    single { NetworkFactory() }
    single { get<NetworkFactory>().createService(NetworkService::class.java) }
}
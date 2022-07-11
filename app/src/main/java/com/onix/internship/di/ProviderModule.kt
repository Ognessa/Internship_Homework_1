package com.onix.internship.di

import com.onix.internship.arch.provider.TextResProvider
import com.onix.internship.objects.MusicPlayer
import com.onix.internship.objects.Scenario
import org.koin.dsl.module

val providerModule = module {
    single { TextResProvider(get()) }
    single { Scenario(get()) }
    single { MusicPlayer(get()) }
}
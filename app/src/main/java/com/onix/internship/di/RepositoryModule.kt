package com.onix.internship.di

import com.onix.internship.data.repository.PreferenceStorage
import com.onix.internship.objects.DataStore
import org.koin.dsl.module

val repositoryModule = module {
    single { PreferenceStorage(get()) }
    single { DataStore(get()) }
}
package com.onix.internship.di

import com.onix.internship.data.repository.PreferenceStorage
import com.onix.internship.objects.DataStore
import com.onix.internship.ui.clearDialog.ClearModel
import org.koin.dsl.module

val repositoryModule = module {
    single { PreferenceStorage(get()) }
    single { DataStore(get()) }
    single { ClearModel() }
}
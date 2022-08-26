package com.onix.internship

import android.app.Application
import com.onix.internship.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class InternshipApp : Application() {

    private val appModules by lazy {
        listOf(mapperModule, networkModule, repositoryModule, providerModule, viewModelModule)
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@InternshipApp)
            modules(appModules)
        }
    }

}
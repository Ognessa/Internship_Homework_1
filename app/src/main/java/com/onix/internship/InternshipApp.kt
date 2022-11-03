package com.onix.internship

import android.app.Application
import com.onix.internship.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InternshipApp : Application() {

    private val appModule = defaultInterceptor + defaultDomainModule + defaultPresentation +
            mappers + networkModule + viewModels

    private val appModules by lazy {
        appModule
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@InternshipApp)
            modules(appModules)
        }
    }
}
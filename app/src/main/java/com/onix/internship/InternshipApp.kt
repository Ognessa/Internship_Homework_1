package com.onix.internship

import android.app.Application
import com.onix.internship.di.defaultDomainModule
import com.onix.internship.di.defaultInterceptor
import com.onix.internship.di.defaultPresentation
import com.onix.internship.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InternshipApp : Application() {

    private val appModule =
        defaultInterceptor + defaultDomainModule + defaultPresentation + viewModels

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
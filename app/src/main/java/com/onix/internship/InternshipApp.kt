package com.onix.internship

import android.app.Application
import com.onix.internship.di.mapperModule
import com.onix.internship.di.networkModule
import com.onix.internship.di.providerModelModule
import com.onix.internship.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InternshipApp : Application() {

    private val appModules by lazy {
        listOf(viewModelModule, providerModelModule, networkModule, mapperModule)
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@InternshipApp)
            modules(appModules)
        }
    }

}
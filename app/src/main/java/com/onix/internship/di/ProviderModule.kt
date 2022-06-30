package com.onix.internship.di

import com.onix.internship.arch.provider.TextResProvider
import com.onix.internship.parser.DictionaryXmlParser
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val providerModule = module {
    single { TextResProvider(get()) }
    single { DictionaryXmlParser(context = androidApplication()) }
}
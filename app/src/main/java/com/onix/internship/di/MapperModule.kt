package com.onix.internship.di

import com.onix.internship.data.mappers.ApiMemeMapper
import com.onix.internship.data.mappers.ApiPageMapper
import org.koin.dsl.module

val mapperModule = module {
    single { ApiPageMapper(get()) }
    single { ApiMemeMapper() }
}
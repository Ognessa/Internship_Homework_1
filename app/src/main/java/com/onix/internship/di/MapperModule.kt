package com.onix.internship.di

import com.onix.internship.data.mappers.ApiDataMapper
import com.onix.internship.data.mappers.ApiRecordingDataMapper
import com.onix.internship.data.mappers.ApiSonoDataMapper
import org.koin.dsl.module

val mapperModule = module {
    single { ApiDataMapper(get()) }
    single { ApiRecordingDataMapper(get()) }
    single {ApiSonoDataMapper()}
}
package com.onix.internship.di

import com.onix.internship.data.mapper.HomeInfoMapper
import com.onix.internship.data.mapper.SensorMapper
import org.koin.dsl.module

val mapperModule = module {
    single { SensorMapper() }
    single { HomeInfoMapper(get()) }
}
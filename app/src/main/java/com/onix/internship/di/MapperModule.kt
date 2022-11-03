package com.onix.internship.di

import com.onix.internship.data.mappers.PersonDataMapper
import org.koin.dsl.module

val mappers = module {
    factory { PersonDataMapper() }
}
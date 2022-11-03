package com.onix.internship.di

import com.onix.internship.data.network.configuration.NetworkFactory
import com.onix.internship.network.PersonDataConfiguration
import com.onix.internship.network.PersonDataInterceptor
import com.onix.internship.network.PersonService
import com.onix.internship.network.useCase.GetPersonDataUseCase
import org.koin.dsl.module

val networkModule = module {
    factory<PersonService> {
        NetworkFactory.createService(
            protocol = PersonService::class.java,
            configuration = PersonDataConfiguration(),
            interceptors = PersonDataInterceptor()
        )
    }

    factory { GetPersonDataUseCase(
        service = get(),
        mapper = get()
    ) }
}
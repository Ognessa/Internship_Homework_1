package com.onix.internship.di

import com.onix.internship.data.network.configuration.NetworkConfiguration
import com.onix.internship.data.network.configuration.interceptor.provider.DefaultInterceptorProvider
import com.onix.internship.data.network.configuration.interceptor.provider.InterceptorProvider
import com.onix.internship.domain.component.device.UserAgentProvider
import com.onix.internship.domain.component.logger.Logger
import org.koin.dsl.module

val defaultInterceptor = module {
    single<InterceptorProvider> {
        DefaultInterceptorProvider(
            configuration = get<NetworkConfiguration>(),
            userAgentProvider = get<UserAgentProvider>(),
            logger = get<Logger>()
        )
    }
}
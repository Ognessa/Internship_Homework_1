package com.onix.internship.data.network.configuration

import com.onix.internship.data.network.configuration.convertor.ConverterFactory
import com.onix.internship.data.network.configuration.convertor.KotlinConverterFactory
import com.onix.internship.data.network.configuration.interceptor.utils.token.TokenProvider

sealed class NetworkConfiguration {
    abstract val server: String

    open val timeout: Long = 30L * 1000

    open val withLogs = true
    open val withAuth = false

    open val converterFactory: ConverterFactory = KotlinConverterFactory()

    abstract class AuthConfiguration(val tokenProvider: TokenProvider) : NetworkConfiguration() {
        override val withAuth: Boolean = true
    }
}
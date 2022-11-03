package com.onix.internship.data.network.configuration.interceptor.utils

import com.onix.internship.domain.component.logger.Level
import com.onix.internship.domain.component.logger.Logger
import okhttp3.logging.HttpLoggingInterceptor

class InterceptorLogger(private val logger: Logger) : HttpLoggingInterceptor.Logger {

    private val loggerConfiguration = logger.getConfiguration().copy(className = "Network")

    override fun log(message: String) {
        logger.log(
            level = Level.Info,
            message = message,
            configuration = loggerConfiguration
        )
    }
}

package com.onix.internship.di

import com.onix.internship.BuildConfig
import com.onix.internship.arch.component.device.LibDeviceInfo
import com.onix.internship.arch.component.logger.AndroidLogger
import com.onix.internship.arch.component.logger.configuration.DefaultLoggerConfiguration
import com.onix.internship.domain.component.device.DeviceInfo
import com.onix.internship.domain.component.device.UserAgent
import com.onix.internship.domain.component.device.UserAgentProvider
import com.onix.internship.domain.component.logger.Logger
import com.onix.internship.domain.component.logger.configuration.LoggerConfiguration
import org.koin.core.module.Module
import org.koin.dsl.module

val defaultLogger = module {
    factory<Logger> {
        AndroidLogger(configuration = get<LoggerConfiguration>())
    }
}

val defaultLoggerConfiguration = module {
    factory<LoggerConfiguration> {
        DefaultLoggerConfiguration(isEnabled = BuildConfig.DEBUG)
    }
}

val defaultUserAgent = module {
    single<UserAgentProvider> {
        UserAgent(deviceInfo = get<DeviceInfo>())
    }
}

val defaultDeviceInfo = module {
    single<DeviceInfo> {
        LibDeviceInfo()
    }
}

val defaultLoggerModule = listOf<Module>(defaultLoggerConfiguration, defaultLogger)
val defaultDeviceInfoModule = listOf<Module>(defaultDeviceInfo, defaultUserAgent)

val defaultDomainModule = defaultLoggerModule + defaultDeviceInfoModule

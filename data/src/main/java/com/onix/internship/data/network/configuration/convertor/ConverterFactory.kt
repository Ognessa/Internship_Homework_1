package com.onix.internship.data.network.configuration.convertor

import retrofit2.Converter

interface ConverterFactory {
    val converterFactory: Converter.Factory
}
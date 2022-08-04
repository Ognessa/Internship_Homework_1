package com.onix.internship.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class NetworkFactory {

    fun <S> createService(protocol: Class<S>): S {
        return retrofit.create(protocol)
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl("https://onix-systems.github.io")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient
            .Builder()
            .addInterceptor(NetworkInterceptor())
            .build()
}
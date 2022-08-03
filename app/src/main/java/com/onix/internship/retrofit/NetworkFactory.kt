package com.onix.internship.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.*
import kotlinx.serialization.json.*
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
            .baseUrl("https://onix-systems.github.io/OnixAndroidInternship2022/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient
            .Builder()
            .addInterceptor(NetworkInterceptor())
            .build()

    companion object {
        fun <S> createService(protocol: Class<S>): S {
            return NetworkFactory().createService(protocol = protocol)
        }
    }
}
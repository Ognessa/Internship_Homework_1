package com.onix.internship.data.network.configuration.interceptor

import com.onix.internship.data.network.configuration.NetworkConfiguration
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val configuration: NetworkConfiguration.AuthConfiguration) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = configuration.tokenProvider.getToken()

        if (token.isNullOrBlank()) {
            return chain.proceed(chain.request())
        }

        val request = chain.request()
            .newBuilder()
            .header(
                AUTHORIZATION_HEADER_KEY,
                "$AUTHORIZATION_HEADER_VALUE $token"
            )
            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val AUTHORIZATION_HEADER_KEY = "Authorization"
        private const val AUTHORIZATION_HEADER_VALUE = "Bearer"
    }

}

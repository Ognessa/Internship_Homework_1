package com.onix.internship.data.network.configuration.interceptor

import com.onix.internship.domain.component.device.UserAgentProvider
import okhttp3.Interceptor
import okhttp3.Response

class UserAgentInterceptor(private val userAgentProvider: UserAgentProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithUserAgent = originalRequest.newBuilder()
            .header(UA_HEADER_KEY, userAgentProvider.userAgent)
            .build()
        return chain.proceed(requestWithUserAgent)
    }

    companion object {
        private const val UA_HEADER_KEY = "User-Agent"
    }
}

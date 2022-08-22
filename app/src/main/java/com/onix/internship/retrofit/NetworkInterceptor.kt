package com.onix.internship.retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class NetworkInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        Log.d("DEBUG", "$request")

        val response = chain.proceed(request)
        val temp = response.body

        return response
    }
}
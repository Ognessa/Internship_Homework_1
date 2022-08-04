package com.onix.internship.network

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class NetworkInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        var temp = response.body?.string()

        val start = "<h3 id=\"data-started\">Data started</h3>\n<p>"
        val end = "</p>\n<h3 id=\"data-ended\">Data ended</h3>"

        temp = temp?.subSequence(temp.indexOf(start) + start.length, temp.indexOf(end)).toString()
            .replace("“", "\"").replace("”", "\"")

        return response.newBuilder().body(temp.toResponseBody("application/json".toMediaType()))
            .build()
    }
}
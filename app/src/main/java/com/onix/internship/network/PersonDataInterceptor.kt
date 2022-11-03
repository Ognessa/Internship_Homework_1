package com.onix.internship.network

import com.onix.internship.data.network.configuration.interceptor.provider.InterceptorProvider
import okhttp3.Interceptor

class PersonDataInterceptor : InterceptorProvider {
    override val networkInterceptors: ArrayList<Interceptor> = arrayListOf()
    override val interceptors: ArrayList<Interceptor> = arrayListOf()
}
package com.onix.internship.data.network.configuration.interceptor.utils.token

interface TokenProvider {
    fun getToken(): String?
}
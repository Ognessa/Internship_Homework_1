package com.onix.internship.data.network.configuration.interceptor.utils.token

class BearerProvider : TokenProvider {
    private fun validateToken() = true
    private fun fetchToken(): String? = null
    private fun loadToken(): String? = null

    @Synchronized
    override fun getToken() = if (validateToken()) {
        loadToken()
    } else {
        fetchToken()
    }

}
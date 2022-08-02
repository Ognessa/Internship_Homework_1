package com.onix.internship.retrofit

import retrofit2.Call
import retrofit2.http.GET


interface ApiService {
    @get:GET(".")
    val stringResponse: Call<String?>?
}
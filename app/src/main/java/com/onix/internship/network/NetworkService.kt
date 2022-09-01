package com.onix.internship.network

import com.onix.internship.objects.api.ApiPageData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {
    @GET("/localememes/{page}")
    suspend fun getMeme(@Path("page") page : Int) : Response<ApiPageData>
}
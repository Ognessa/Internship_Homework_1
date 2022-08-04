package com.onix.internship.network

import com.onix.internship.entity.network.ApiHomeInfo
import retrofit2.Response
import retrofit2.http.GET

interface NetworkService {
    @GET("/OnixAndroidInternship2022")
    suspend fun getJsonData(): Response<ApiHomeInfo>
}
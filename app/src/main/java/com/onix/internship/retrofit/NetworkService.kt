package com.onix.internship.retrofit

import com.onix.internship.objects.apiObjects.ApiData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("/api/2/recordings")
    suspend fun search(@Query("query")query : String) : Response<ApiData>
}
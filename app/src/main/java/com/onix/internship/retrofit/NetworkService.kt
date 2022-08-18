package com.onix.internship.retrofit

import com.onix.internship.objects.apiObjects.ApiData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("/api/2/recordings")
    suspend fun simpleSearch(@Query("query")query : String) : Response<ApiData>

    @GET("/api/2/recordings")
    suspend fun advancedSearch(@Query("query") query : String) : Response<ApiData>
}
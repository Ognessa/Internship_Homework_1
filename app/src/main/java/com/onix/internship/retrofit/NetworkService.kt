package com.onix.internship.retrofit

import com.onix.internship.objects.JsonData
import retrofit2.Call
import retrofit2.http.GET

interface NetworkService {
    @GET("/OnixAndroidInternship2022")
    public fun getJsonData() : Call<JsonData>
}
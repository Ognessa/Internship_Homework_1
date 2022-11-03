package com.onix.internship.network

import com.onix.internship.entity.remote.PersonData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonService {
    @GET("/")
    suspend fun getPersonData(@Query("name") name: String): Response<PersonData>
}
package com.onix.internship.objects.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiPageData (
    @SerialName("code") val code : Int,
    @SerialName("data") val data : List<ApiMemeData>,
    @SerialName("message") val message : String,
    @SerialName("next") val next : String
)
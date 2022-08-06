package com.onix.internship.entity.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiHomeInfo(
    @SerialName("version") val version : String,
    @SerialName("name") val name : String,
    @SerialName("house") val house : List<ApiDeviceData>
)
package com.onix.internship.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonData(
    @SerialName("version") val version : String,
    @SerialName("name") val name : String,
    @SerialName("house") val house : List<DeviceData>
)
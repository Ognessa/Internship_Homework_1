package com.onix.internship.entity.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiDeviceData(
    @SerialName("room") val room: String,
    @SerialName("type") val type: String,
    @SerialName("subtype") val subtype: String,
    @SerialName("value") val value: String
)
package com.onix.internship.objects

import kotlinx.serialization.*

@Serializable
data class DeviceData(
    @SerialName("room") val room : String,
    @SerialName("type") val type : String,
    @SerialName("subtype") val subtype : String,
    @SerialName("value") val value : String
)
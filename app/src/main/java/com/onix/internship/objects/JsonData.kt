package com.onix.internship.objects

import kotlinx.serialization.Serializable

@Serializable
data class JsonData(
    val version : String,
    val name : String,
    val house : List<DeviceData>
)
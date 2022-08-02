package com.onix.internship.objects

import kotlinx.serialization.*

@Serializable
data class DeviceData(
    val room : String,
    val type : String,
    val subtype : String,
    val value : String
)
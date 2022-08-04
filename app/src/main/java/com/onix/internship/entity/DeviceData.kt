package com.onix.internship.entity

data class DeviceData(
    val room: String,
    val type: String,
    val subType: SensorSubType,
    val value: String
)

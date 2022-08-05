package com.onix.internship.entity

data class DeviceData(
    var room: String,
    var type: SensorType,
    var subType: SensorSubType,
    val value: String
)

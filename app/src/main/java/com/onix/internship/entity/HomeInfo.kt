package com.onix.internship.entity

data class HomeInfo(
    val version: String,
    val name: String,
    val house: List<DeviceData>
)

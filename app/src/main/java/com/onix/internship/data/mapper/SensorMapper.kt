package com.onix.internship.data.mapper

import com.onix.internship.entity.DeviceData
import com.onix.internship.entity.SensorSubType
import com.onix.internship.entity.SensorType
import com.onix.internship.entity.network.ApiDeviceData

class SensorMapper {
    fun map(apiDeviceData: ApiDeviceData): DeviceData {

        val subType = when (apiDeviceData.subtype) {
            "switch" -> SensorSubType.SWITCH
            "onetime" -> SensorSubType.ONETIME
            "level" -> SensorSubType.LEVEL
            else -> SensorSubType.SWITCH
        }

        val type = when(apiDeviceData.type) {
            "Sensor" -> SensorType.SENSOR
            "Camera" -> SensorType.CAMERA
            "Sound" -> SensorType.SOUND
            "Light" -> SensorType.LIGHT
            else -> SensorType.SENSOR
        }

        return DeviceData(
            room = apiDeviceData.room,
            type = type,
            subType = subType,
            value = apiDeviceData.value
        )
    }
}
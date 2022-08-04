package com.onix.internship.data.mapper

import com.onix.internship.entity.DeviceData
import com.onix.internship.entity.SensorSubType
import com.onix.internship.entity.network.ApiDeviceData

class SensorMapper {
    fun map(apiDeviceData: ApiDeviceData): DeviceData {
        val subType = when (apiDeviceData.subtype) {
            "switch" -> SensorSubType.SWITCH
            "onetime" -> SensorSubType.ONETIME
            "level" -> SensorSubType.LEVEL
            else -> {
                SensorSubType.SWITCH
            }
        }
        return DeviceData(
            room = apiDeviceData.room,
            type = apiDeviceData.type,
            subType = subType,
            value = apiDeviceData.value
        )
    }
}
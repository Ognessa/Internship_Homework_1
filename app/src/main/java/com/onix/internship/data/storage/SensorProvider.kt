package com.onix.internship.data.storage

import com.onix.internship.entity.DeviceData
import com.onix.internship.entity.SensorSubType
import kotlin.random.Random

class SensorProvider {
    fun createSensor(room: String?, type: String?, subType: SensorSubType?): DeviceData {
        val currentSubType = subType ?: SensorSubType.SWITCH
        return DeviceData(
            room ?: "Default value",
            type ?: "Sensor",
            currentSubType,
            createRandomValue(currentSubType)
        )
    }

    private fun createRandomValue(subType: SensorSubType): String {
        val value = when (subType) {
            SensorSubType.SWITCH -> {
                if (Random.nextInt(0, 2) == 0) "OFF"
                else "ON"
            }
            SensorSubType.ONETIME -> {
                "ONETIME"
            }
            SensorSubType.LEVEL -> {
                "${Random.nextInt(1, 10)}"
            }
        }
        return value
    }
}
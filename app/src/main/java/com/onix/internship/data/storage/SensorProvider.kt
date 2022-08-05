package com.onix.internship.data.storage

import com.onix.internship.entity.DeviceData
import com.onix.internship.entity.SensorSubType
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.random.Random

class SensorProvider {

    fun createSensor(data: DeviceData): DeviceData {
        return DeviceData(data.room, data.type, data.subType, createRandomValue(data.subType))
    }

    private fun createRandomValue(subType: SensorSubType): String {
        return when (subType) {
            SensorSubType.SWITCH -> {
                if (Random.nextBoolean()) "off"
                else "on"
            }
            SensorSubType.ONETIME -> {
                when(Random.nextInt(0, 2)){
                    0 -> "https://th.bing.com/th/id/OIP.yMs1Vsh5TGUxRsO-4I3ybgHaEK?pid=ImgDet&rs=1"
                    1 -> "https://th.bing.com/th/id/OIP.vODlsBQh4ViNiHG3ubN0YAHaEK?pid=ImgDet&rs=1"
                    2 -> "https://th.bing.com/th/id/OIP.2telEFX5XYwyCIDcZsETSAHaE7?pid=ImgDet&rs=1"
                    else -> "https://th.bing.com/th/id/OIP.yMs1Vsh5TGUxRsO-4I3ybgHaEK?pid=ImgDet&rs=1"
                }
            }
            SensorSubType.LEVEL -> {
                if(Random.nextBoolean())
                    Random.nextInt(1, 10).toString()
                else
                    String.format("%.2f", Random.nextDouble(0.0, 10.0))
            }
        }
    }
}
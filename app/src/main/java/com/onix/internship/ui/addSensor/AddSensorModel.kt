package com.onix.internship.ui.addSensor

import androidx.databinding.ObservableField
import com.onix.internship.entity.DeviceData
import com.onix.internship.entity.SensorSubType
import com.onix.internship.entity.SensorType

data class AddSensorModel(
    val deviceData: ObservableField<DeviceData> = ObservableField<DeviceData>(
        DeviceData("Room", SensorType.SENSOR, SensorSubType.SWITCH, "On")
    )
)

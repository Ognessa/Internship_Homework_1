package com.onix.internship.data.storage

import com.onix.internship.entity.DeviceData

class SensorStorage {
    private val sensorsList = mutableListOf<DeviceData>()

    fun getSensorsList(): List<DeviceData> {
        return sensorsList.distinct()
    }

    fun addNewSensorInList(sensor: List<DeviceData>) {
        sensorsList.removeAll(sensor.toSet())
        sensorsList.addAll(sensor)
    }

    fun addNewSensorInList(sensor: DeviceData) {
        sensorsList.remove(sensor)
        sensorsList.add(sensor)
    }

    fun deleteSensorFromList(sensor: DeviceData) {
        sensorsList.remove(sensor)
    }
}
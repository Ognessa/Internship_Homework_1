package com.onix.internship.ui.sensors.adapter

import com.onix.internship.entity.DeviceData

interface OnSensorClickListener {
    fun deleteSensor(item: DeviceData)
}
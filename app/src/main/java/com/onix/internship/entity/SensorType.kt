package com.onix.internship.entity

import androidx.annotation.StringRes
import com.onix.internship.R

enum class SensorType(@StringRes val res : Int) {
    SENSOR(R.string.sensor_type), CAMERA(R.string.camera_type),
    SOUND(R.string.sound_type), LIGHT(R.string.light_type);

    fun getValue() : Int{
        return res
    }
}
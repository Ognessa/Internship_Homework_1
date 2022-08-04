package com.onix.internship.ui.addSensor

import androidx.databinding.ObservableField
import com.onix.internship.entity.SensorSubType

data class AddSensorModel(
    val room: ObservableField<String> = ObservableField(),
    val type: ObservableField<String> = ObservableField(),
    val subType: ObservableField<SensorSubType> = ObservableField()
)

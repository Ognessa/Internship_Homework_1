package com.onix.internship.ui.addSensor

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.data.storage.SensorProvider
import com.onix.internship.data.storage.SensorStorage
import com.onix.internship.entity.SensorSubType
import com.onix.internship.entity.SensorType

class AddSensorViewModel(
    private val sensorStorage: SensorStorage,
    private val sensorProvider: SensorProvider
) : BaseViewModel() {

    val moveBack = SingleLiveEvent<Unit>()

    private val model = AddSensorModel()

    fun updateRoom(room : String){
        val deviceData = model.deviceData.get()
        deviceData?.room = room
        model.deviceData.set(deviceData)
    }

    fun updateType(type : SensorType){
        val deviceData = model.deviceData.get()
        deviceData?.type = type
        model.deviceData.set(deviceData)
    }

    fun updateSubtype(subtype : SensorSubType){
        val deviceData = model.deviceData.get()
        deviceData?.subType = subtype
        model.deviceData.set(deviceData)
    }

    fun addSensor() {
        model.deviceData.get()?.let {
            sensorStorage.addNewSensorInList(sensorProvider.createSensor(it))
            moveBack.value = Unit
        }
    }
}
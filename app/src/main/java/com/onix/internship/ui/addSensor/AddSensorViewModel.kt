package com.onix.internship.ui.addSensor

import androidx.lifecycle.LiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.data.storage.SensorProvider
import com.onix.internship.data.storage.SensorStorage

class AddSensorViewModel(
    private val sensorStorage: SensorStorage,
    private val sensorProvider: SensorProvider
) : BaseViewModel() {

    private val _moveBack = SingleLiveEvent<Unit>()
    val moveBack: LiveData<Unit> = _moveBack

    val model = AddSensorModel()

    fun addSensor() {
        val sensor =
            sensorProvider.createSensor(model.room.get(), model.type.get(), model.subType.get())
        sensorStorage.addNewSensorInList(sensor)
        _moveBack.value = Unit
    }
}
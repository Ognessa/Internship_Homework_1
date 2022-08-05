package com.onix.internship.ui.sensors

import androidx.lifecycle.viewModelScope
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.data.repository.SensorRepository
import com.onix.internship.data.storage.SensorStorage
import com.onix.internship.entity.DeviceData
import com.onix.internship.ui.sensors.adapter.OnSensorClickListener
import com.taekscode.network.response.onFailure
import com.taekscode.network.response.onSuccess
import kotlinx.coroutines.launch

class SensorsListViewModel(
    private val sensorStorage: SensorStorage,
    private val repository: SensorRepository,
    val model: SensorsListModel
) : BaseViewModel(), OnSensorClickListener {

    val moveToAddFragment = SingleLiveEvent<Unit>()

    init {
        getDataFromApi()
    }

    fun addNewSensor() {
        moveToAddFragment.value = Unit
    }

    fun updateListItem() {
        model.mutableListOfSensors.postValue(sensorStorage.getSensorsList().toList())
    }

    override fun deleteSensor(item: DeviceData) {
        sensorStorage.deleteSensorFromList(item)
        updateListItem()
    }

    fun getDataFromApi() {
        viewModelScope.launch {
            model.isRefreshing.set(true)
            repository.getSensors()
                .onSuccess {
                    sensorStorage.addNewSensorInList(it)
                    updateListItem()
                }
                .onFailure {
                    showMsgError(it.message)
                }

            model.isRefreshing.set(false)
        }
    }
}
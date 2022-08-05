package com.onix.internship.ui.sensors

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.entity.DeviceData

class SensorsListModel {
    val isRefreshing = ObservableBoolean(false)

    val mutableListOfSensors = MutableLiveData<List<DeviceData>>()
    val listOfSensors: LiveData<List<DeviceData>> = mutableListOfSensors
}
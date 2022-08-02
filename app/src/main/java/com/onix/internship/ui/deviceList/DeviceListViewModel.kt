package com.onix.internship.ui.deviceList

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.data.repository.DevicesStorage
import com.onix.internship.objects.DeviceData
import com.onix.internship.retrofit.ResponseProvider


class DeviceListViewModel(private val devicesStorage: DevicesStorage) : BaseViewModel() {

    val navigateToAddDevices = SingleLiveEvent<Unit>()
    val responseProvider = ResponseProvider()

    fun getDeviceList(): MutableList<DeviceData> {
        return devicesStorage.deviceList
    }

    fun addNewDevice(){
        navigateToAddDevices.postValue(Unit)
    }

    fun getDataFromApi(){
        responseProvider.getDataFromApi()
    }



}
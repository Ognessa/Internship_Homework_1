package com.onix.internship.ui.deviceList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.data.repository.DevicesStorage
import com.onix.internship.objects.DeviceData
import com.onix.internship.objects.JsonData
import com.onix.internship.retrofit.NetworkFactory
import com.onix.internship.retrofit.NetworkService
import retrofit2.Call
import retrofit2.Response


class DeviceListViewModel(
    private val devicesStorage: DevicesStorage,
    private val networkFactory: NetworkFactory
    ) : BaseViewModel() {

    val navigateToAddDevices = SingleLiveEvent<Unit>()

    private val _deviceList = MutableLiveData<MutableList<DeviceData>>()
    val deviceList : LiveData<MutableList<DeviceData>> get() = _deviceList

    fun getDeviceList(): MutableList<DeviceData> {
        return devicesStorage.deviceList
    }

    fun addNewDevice(){
        navigateToAddDevices.postValue(Unit)
    }

    fun getDataFromApi(){
        val service : NetworkService = networkFactory.createService(NetworkService::class.java)
        val call = service.getJsonData()

        call.enqueue(object : retrofit2.Callback<JsonData> {
            override fun onResponse(call: Call<JsonData>, response: Response<JsonData>) {
                val jsonData = response.body()
                updateList(jsonData)

            }

            override fun onFailure(call: Call<JsonData>, t: Throwable) {
                showMsgError("FAILURE")
            }

        })

    }

    fun updateList(jsonData: JsonData?){
        val list = mutableListOf<DeviceData>()

        jsonData?.house?.forEach {
            list.add(it)
        }
        list.addAll(devicesStorage.deviceList)

        _deviceList.postValue(list)
    }

}
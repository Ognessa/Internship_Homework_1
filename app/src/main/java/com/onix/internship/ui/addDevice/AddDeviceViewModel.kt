package com.onix.internship.ui.addDevice

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.data.repository.DevicesStorage
import com.onix.internship.objects.DeviceData
import kotlin.random.Random

class AddDeviceViewModel(private val devicesStorage: DevicesStorage) : BaseViewModel(){

    val returnToPrevious = SingleLiveEvent<Unit>()
    private val model = AddDeviceModel()

    fun updateRoom(roomTitle : String){
        model.room.set(roomTitle)
    }

    fun updateDevice(deviceTitle : String){
        model.device.set(deviceTitle)
    }

    fun updateSubtype(deviceTitle : String) {
        model.subtype.set(deviceTitle)
    }

    fun addNewDevice(){
        val device = DeviceData(
            model.room.get().toString(),
            model.device.get().toString(),
            model.subtype.get().toString(),
            getRandomValue()
        )
        devicesStorage.deviceList.add(device)
        returnToPrevious.postValue(Unit)
    }

    private fun getRandomValue(): String {
        val random = Random.nextInt(0, 2)
        return when(random){
            0 -> "https://www.wrenkitchens.com/blog/wp-content/uploads/2021/12/2022-kitchen-design-trends-dark-kitchen-2048x1366.jpg"
            else -> "0.0"
        }
    }
}
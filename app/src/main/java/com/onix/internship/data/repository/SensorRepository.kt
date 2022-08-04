package com.onix.internship.data.repository

import com.onix.internship.data.mapper.HomeInfoMapper
import com.onix.internship.entity.DeviceData
import com.onix.internship.network.NetworkService
import java.lang.Error

class SensorRepository(
    private val networkService: NetworkService,
    private val homeInfoMapper: HomeInfoMapper
) {
    suspend fun getSensors(): List<DeviceData> {
        return try {
            val response = networkService.getJsonData()
            val body = response.body()

            if(!response.isSuccessful || body == null) {
                listOf()
            } else {
                homeInfoMapper.map(body).house
            }
        } catch (e:Error){
            listOf()
        }
    }
}
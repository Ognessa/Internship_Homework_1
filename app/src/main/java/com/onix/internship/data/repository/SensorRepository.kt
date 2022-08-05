package com.onix.internship.data.repository

import com.onix.internship.data.mapper.HomeInfoMapper
import com.onix.internship.entity.DeviceData
import com.onix.internship.network.NetworkService
import com.onix.internship.network.Either
import java.lang.Error

class SensorRepository(
    private val networkService: NetworkService,
    private val homeInfoMapper: HomeInfoMapper
) {
    suspend fun getSensors(): Either<List<DeviceData>> {
        return try {
            val response = networkService.getJsonData()
            val body = response.body()

            if(!response.isSuccessful || body == null) {
                Either.failure(Throwable("Request error"))
            } else {
                Either.success(homeInfoMapper.map(body).house)
            }
        } catch (e:Error){
            Either.failure(e)
        }
    }
}
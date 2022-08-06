package com.onix.internship.data.mapper

import com.onix.internship.entity.HomeInfo
import com.onix.internship.entity.network.ApiHomeInfo

class HomeInfoMapper(private val sensorMapper: SensorMapper) {
    fun map(apiHomeInfo: ApiHomeInfo): HomeInfo {
        return HomeInfo(
            version = apiHomeInfo.version,
            name = apiHomeInfo.name,
            house = apiHomeInfo.house.map { sensorMapper.map(it) }
        )
    }
}
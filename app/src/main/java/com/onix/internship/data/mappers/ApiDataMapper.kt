package com.onix.internship.data.mappers

import com.onix.internship.objects.apiObjects.ApiData
import com.onix.internship.objects.localObjects.AllData

class ApiDataMapper(private val recordingDataMapper: ApiRecordingDataMapper) : Mapper<ApiData, AllData>() {
    override fun map(from: ApiData): AllData {
        return AllData(
            numRecordings = from.numRecordings,
            numSpecies = from.numSpecies,
            page = from.page,
            numPages = from.numPages,
            recordings = from.recordings.map { recordingDataMapper.map(it) }
        )
    }

}
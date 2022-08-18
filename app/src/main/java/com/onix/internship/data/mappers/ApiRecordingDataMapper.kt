package com.onix.internship.data.mappers

import com.onix.internship.objects.apiObjects.ApiRecordingData
import com.onix.internship.objects.localObjects.RecordingData

class ApiRecordingDataMapper(private val apiSonoDataMapper: ApiSonoDataMapper) : Mapper<ApiRecordingData, RecordingData>() {
    override fun map(from: ApiRecordingData): RecordingData {
        return  RecordingData(
            id = from.id,
            gen = from.gen,
            sp = from.sp,
            ssp = from.ssp,
            en = from.en,
            rec = from.rec,
            cnt = from.cnt,
            loc = from.loc,
            lat = from.lat,
            lng = from.lng,
            alt = from.alt,
            type = from.type,
            url = from.url,
            file = from.file,
            fileName = from.fileName,
            sono = apiSonoDataMapper.map(from.sono),
            lic = from.lic,
            q = from.q,
            length = from.length,
            time = from.time,
            date = from.date,
            uploaded = from.uploaded,
            also = from.also,
            rmk = from.rmk,
            birdSeen = from.birdSeen,
            playbackUsed = from.playbackUsed
        )
    }

}
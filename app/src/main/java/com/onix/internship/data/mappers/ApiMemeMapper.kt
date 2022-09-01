package com.onix.internship.data.mappers

import com.onix.internship.arch.mapper.Mapper
import com.onix.internship.objects.api.ApiMemeData
import com.onix.internship.objects.local.MemeData

class ApiMemeMapper : Mapper<ApiMemeData, MemeData>() {
    override fun map(from: ApiMemeData): MemeData {
        return MemeData(
            id = from.id,
            bottomText = from.bottomText ?: "",
            image = from.image ?: "",
            name = from.name ?: "",
            tags = from.tags ?: "",
            topText = from.topText ?: ""
        )
    }
}
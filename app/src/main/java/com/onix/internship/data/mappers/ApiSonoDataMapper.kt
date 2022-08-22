package com.onix.internship.data.mappers

import com.onix.internship.objects.apiObjects.ApiSonoData
import com.onix.internship.objects.localObjects.SonoData

class ApiSonoDataMapper : Mapper<ApiSonoData, SonoData>() {
    override fun map(from: ApiSonoData): SonoData {
        return SonoData(
            small = from.small,
            med = from.large,
            large = from.large,
            full = from.full
        )
    }
}
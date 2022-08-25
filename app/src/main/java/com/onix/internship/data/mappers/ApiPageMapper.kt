package com.onix.internship.data.mappers

import com.onix.internship.arch.mapper.Mapper
import com.onix.internship.objects.api.ApiPageData
import com.onix.internship.objects.local.PageData

class ApiPageMapper(private val apiMemeMapper : ApiMemeMapper) : Mapper<ApiPageData, PageData>() {
    override fun map(from: ApiPageData): PageData {
        return PageData(
            code = from.code,
            data = from.data.map { apiMemeMapper.map(it) },
            message = from.message,
            next = from.next
        )
    }
}
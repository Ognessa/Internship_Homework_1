package com.onix.internship.data.repository

import com.onix.internship.data.mappers.ApiPageMapper
import com.onix.internship.network.MemesPagingSource
import com.onix.internship.network.Network

class MemeRepository(
    private val network: Network,
    private val mapper: ApiPageMapper
) {
    fun memesPagingSource() = MemesPagingSource(network, mapper)
}
package com.onix.internship.data.repository

import com.onix.internship.network.MemesPagingSource
import com.onix.internship.network.Network

class MemeRepository(
    private val network: Network
) {
    var filter : String = "All"

    fun memesPagingSource() = MemesPagingSource(network)
}
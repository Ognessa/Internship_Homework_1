package com.onix.internship.network

import com.onix.internship.arch.mapper.Either
import com.onix.internship.data.mappers.ApiPageMapper
import com.onix.internship.objects.local.MemeData

class Network(
    private val networkService: NetworkService,
    private val mapper: ApiPageMapper
) {

    suspend fun searchMemes(page: Int): Either<List<MemeData>> {
        return try {
            val response = networkService.getMeme(page)
            val body = response.body()

            if (!response.isSuccessful || body == null) {
                Either.failure(Throwable("Request error"))
            } else {
                val list = mapper.map(body).data
                Either.success(list)
            }
        } catch (e: Error) {
            Either.failure(e)
        }
    }

}
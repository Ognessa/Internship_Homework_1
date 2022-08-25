package com.onix.internship.network

import com.onix.internship.arch.mapper.Either
import com.onix.internship.objects.api.ApiPageData

class Network(
    private val networkService: NetworkService
) {

    suspend fun searchMemes(page: Int): Either<ApiPageData> {
        return try {
            val response = networkService.getMeme(page)
            val body = response.body()

            if (!response.isSuccessful || body == null) {
                Either.failure(Throwable("Request error"))
            } else {
                Either.success(body)
            }
        } catch (e: Error) {
            Either.failure(e)
        }
    }

}
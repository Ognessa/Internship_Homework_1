package com.onix.internship.data.network

import com.onix.internship.domain.entities.Either
import com.onix.internship.domain.entities.failure.Failure
import com.onix.internship.domain.entities.failure.HostFailure
import com.onix.internship.domain.entities.failure.ServerError
import retrofit2.Response
import java.net.UnknownHostException

suspend inline fun <T : Any> call(
    crossinline call: suspend () -> Response<T>
): Either<Failure, T> {
    return try {
        val response = call.invoke()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Either.Right(body)
            } else {
                Either.Left(ServerError.ServerCommon)
            }
        } else {
            Either.Left(ServerError.ServerCommon)
        }
    } catch (e: Throwable) {
        handleException(e)
    }
}

@PublishedApi
internal fun handleException(e: Throwable): Either.Left<Failure> {
    if (e is UnknownHostException) {
        return Either.Left(HostFailure)
    }
    return Either.Left(Failure.Common(e))
}
package com.onix.internship.domain.usecase

import com.onix.internship.domain.entities.Either
import com.onix.internship.domain.entities.failure.Failure
import kotlinx.coroutines.flow.Flow

abstract class SubscribeUseCase<out Result : Any> : UseCase<Result> {

    abstract suspend fun execute(param: UseCase.Params? = null): Flow<Either<Failure, Result>>

    suspend operator fun invoke(
        param: UseCase.Params? = null,
        onResult: (Flow<Either<Failure, Result>>) -> Unit = {}
    ) = onResult(execute(param))

}
package com.onix.internship.domain.usecase

import com.onix.internship.domain.entities.Either
import com.onix.internship.domain.entities.failure.Failure

abstract class AsyncUseCase<out Result : Any> : UseCase<Result> {

    abstract suspend fun execute(param: UseCase.Params? = null): Either<Failure, Result>

    suspend operator fun invoke(
        param: UseCase.Params? = null,
        onResult: (Either<Failure, Result>) -> Unit = {}
    ) = onResult(execute(param))

}
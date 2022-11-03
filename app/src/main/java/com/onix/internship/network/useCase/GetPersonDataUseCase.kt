package com.onix.internship.network.useCase

import com.onix.internship.data.mappers.PersonDataMapper
import com.onix.internship.domain.entities.Either
import com.onix.internship.domain.entities.failure.Failure
import com.onix.internship.entity.local.PersonDataEntity
import com.onix.internship.network.PersonService

class GetPersonDataUseCase(
    private val service: PersonService,
    private val mapper: PersonDataMapper
) {

    suspend fun execute(name: String): Either<Failure, PersonDataEntity> {
        val result = service.getPersonData(name)
        val body = result.body()
        return if (result.isSuccessful && body != null) {
            Either.Right(mapper.map(body))
        } else {
            Either.Left(Failure.Common())
        }
    }
}
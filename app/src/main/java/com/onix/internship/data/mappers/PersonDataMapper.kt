package com.onix.internship.data.mappers

import com.onix.internship.domain.converter.Mapper
import com.onix.internship.entity.local.PersonDataEntity
import com.onix.internship.entity.remote.PersonData

class PersonDataMapper : Mapper<PersonData, PersonDataEntity>() {
    override fun map(from: PersonData): PersonDataEntity {
        return PersonDataEntity(
            age = from.age ?: 0,
            count = from.count ?: 0,
            name = from.name ?: ""
        )
    }
}
package com.onix.internship.entity.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonData(
    @SerialName("age") val age: Int? = 0,
    @SerialName("count") val count: Int? = 0,
    @SerialName("name") val name: String? = ""
)
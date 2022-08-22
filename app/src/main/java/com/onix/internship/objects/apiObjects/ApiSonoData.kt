package com.onix.internship.objects.apiObjects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiSonoData(
    @SerialName("small") val small : String,
    @SerialName("med") val med : String,
    @SerialName("large") val large : String,
    @SerialName("full") val full : String
)

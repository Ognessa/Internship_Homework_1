package com.onix.internship.objects.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiMemeData(
    @SerialName("ID") val id : Int,
    @SerialName("bottomText") val bottomText : String?,
    @SerialName("image") val image : String?,
    @SerialName("name") val name : String?,
    @SerialName("tags") val tags : String?,
    @SerialName("topText") val topText : String?
)
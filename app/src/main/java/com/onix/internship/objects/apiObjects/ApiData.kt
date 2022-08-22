package com.onix.internship.objects.apiObjects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiData(
    @SerialName("numRecordings") val numRecordings : String,
    @SerialName("numSpecies") val numSpecies : String,
    @SerialName("page") val page : Int,
    @SerialName("numPages") val numPages : Int,
    @SerialName("recordings") val recordings : List<ApiRecordingData>
)
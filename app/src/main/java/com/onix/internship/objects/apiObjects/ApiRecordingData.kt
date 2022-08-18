package com.onix.internship.objects.apiObjects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiRecordingData(
    @SerialName("id") val id : String,
    @SerialName("gen") val gen : String,
    @SerialName("sp") val sp : String,
    @SerialName("ssp") val ssp : String,
    @SerialName("en") val en : String,
    @SerialName("rec") val rec : String,
    @SerialName("cnt") val cnt : String,
    @SerialName("loc") val loc : String,
    @SerialName("lat") val lat : String?,
    @SerialName("lng") val lng : String?,
    @SerialName("alt") val alt : String,
    @SerialName("type") val type : String,
    @SerialName("url") val url : String,
    @SerialName("file") val file : String,
    @SerialName("file-name") val fileName : String,
    @SerialName("sono") val sono : ApiSonoData,
    @SerialName("lic") val lic : String,
    @SerialName("q") val q : String,
    @SerialName("length") val length : String,
    @SerialName("time") val time : String,
    @SerialName("date") val date : String,
    @SerialName("uploaded") val uploaded : String,
    @SerialName("also") val also : List<String>,
    @SerialName("rmk") val rmk : String,
    @SerialName("bird-seen") val birdSeen : String,
    @SerialName("playback-used") val playbackUsed : String
)

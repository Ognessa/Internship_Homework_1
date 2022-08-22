package com.onix.internship.objects.localObjects

data class AllData (
    val numRecordings : String,
    val numSpecies : String,
    val page : Int,
    val numPages : Int,
    val recordings : List<RecordingData>
)
package com.onix.internship.objects

import com.google.android.gms.maps.model.LatLng

data class Point (
    val location : LatLng,
    val date: String,
    val time: String,
    val pointClass : Int,
    val level : Int
    )

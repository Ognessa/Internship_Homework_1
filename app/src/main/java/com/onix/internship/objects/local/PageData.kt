package com.onix.internship.objects.local

data class PageData (
    val code : Int = 200,
    val data : List<MemeData> = listOf(),
    val message : String = "",
    val next : String = ""
)
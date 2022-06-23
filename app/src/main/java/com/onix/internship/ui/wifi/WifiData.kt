package com.onix.internship.ui.wifi

import android.net.wifi.ScanResult

data class WifiData(
    val scanResult : ScanResult,
    val shortTitle : String,
    val radius : Int,
    val angle : Float)
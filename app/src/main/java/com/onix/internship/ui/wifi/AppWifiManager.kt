package com.onix.internship.ui.wifi

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.random.Random

class AppWifiManager(context : Context){
    val wifiManager : WifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private val resultList : ArrayList<WifiData> = arrayListOf()
    private val MAX_LIST_SIZE = 16
    private val SHORT_TITLE_LENGTH = 4

    private val wifiReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val wifiScanList : List<ScanResult> = wifiManager.scanResults
            updateList(wifiScanList)
        }
    }

    init {
        //wifiManager.startScan()
        //context.registerReceiver(wifiReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
    }

    fun getResultList() : ArrayList<WifiData> {
        return resultList
    }

    private fun updateList(list : List<ScanResult>){
        val tempList = arrayListOf<WifiData>()

        if(list.size <= MAX_LIST_SIZE){
            list.forEach { it ->
                val index = list.indexOf(it)
                tempList.add(WifiData(
                    it,
                    getShortTitle(it.SSID),
                    abs(it.level),
                    360.0f / MAX_LIST_SIZE * index)
                )
            }
        } else {
            for (i in 0..MAX_LIST_SIZE)
                tempList.add(WifiData(
                    list[i],
                    getShortTitle(list[i].SSID),
                    abs(list[i].level),
                    360.0f / MAX_LIST_SIZE * i)
                )
        }

        resultList.clear()
        resultList.addAll(tempList)
    }

    private fun getShortTitle(title : String) : String{
        if(title.length > SHORT_TITLE_LENGTH)
            return title.subSequence(0, SHORT_TITLE_LENGTH).toString()

        return title
    }
}
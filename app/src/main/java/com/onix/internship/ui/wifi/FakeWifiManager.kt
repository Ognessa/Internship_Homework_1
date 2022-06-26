package com.onix.internship.ui.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import kotlin.random.Random

data class FakeWifiData(
    val title : String,
    val level : Int)

class FakeWifiManager(context : Context) {
    val wifiManager : WifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private val fakeResultList : ArrayList<FakeWifiData> = arrayListOf()
    private val MAX_LIST_SIZE = 16

    private val fakeWifiReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val fakeList = generateFakeWifiList()
            updateFakeWifiList(fakeList)

        }
    }

    init {
        context.registerReceiver(fakeWifiReceiver,
            IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        wifiManager.startScan()
    }

    fun updateFakeWifiList(fakeList : ArrayList<FakeWifiData>){
        var tempList = arrayListOf<FakeWifiData>()
        if(fakeList.size <= MAX_LIST_SIZE)
            tempList = fakeList
        else
            tempList = (fakeList.subList(0, MAX_LIST_SIZE-1)) as ArrayList<FakeWifiData>

        fakeResultList.clear()
        fakeResultList.addAll(tempList)
    }

    fun generateFakeWifiList() : ArrayList<FakeWifiData>{
        var fakeList = arrayListOf<FakeWifiData>()
        val count = Random.nextInt(20)
        for( i in 0..count){
            val fakeData = FakeWifiData(i.toString(), Random.nextInt(-99, 0))
            fakeList.add(fakeData)
        }
        fakeList = (fakeList.sortedWith(compareBy { it.level })) as ArrayList<FakeWifiData>
        return fakeList
    }

    fun getFakeWifiResult() : ArrayList<FakeWifiData>{
        return fakeResultList
    }
}
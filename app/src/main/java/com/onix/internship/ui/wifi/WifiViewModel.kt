package com.onix.internship.ui.wifi

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.onix.internship.arch.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.R)
class WifiViewModel(fakeWifiManager : FakeWifiManager) : BaseViewModel() {
    private val _dataList = MutableLiveData<ArrayList<FakeWifiData>>()
    val dataList : LiveData<ArrayList<FakeWifiData>>
        get() = _dataList

    init {
        viewModelScope.launch {
            while (true){
                fakeWifiManager.wifiManager.startScan()
                val fakeList = fakeWifiManager.getFakeWifiResult()
                _dataList.postValue(fakeList)

                Log.d("DEBUG", "Start scan")
                fakeList.forEach {
                    Log.d("DEBUG", "${it.title} -> ${it.level}, " +
                            "${fakeWifiManager.wifiManager.calculateSignalLevel(it.level)}")
                }
                delay(1000)
            }
        }
    }
}
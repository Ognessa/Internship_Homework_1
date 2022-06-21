package com.onix.internship.ui.wifi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.onix.internship.arch.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WifiViewModel(appWifiManager : AppWifiManager) : BaseViewModel() {
    private val _dataList = MutableLiveData<ArrayList<WifiData>>()
    val dataList : LiveData<ArrayList<WifiData>>
        get() = _dataList

    init {
        viewModelScope.launch {
            while (true){
                delay(3000)
                appWifiManager.wifiManager.startScan()
                _dataList.postValue(appWifiManager.getResultList())
            }
        }
    }
}
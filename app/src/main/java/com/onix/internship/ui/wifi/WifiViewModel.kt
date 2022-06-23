package com.onix.internship.ui.wifi

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
                appWifiManager.wifiManager.startScan()
                _dataList.postValue(appWifiManager.getResultList())
                delay(1000)
            }
        }
    }
}
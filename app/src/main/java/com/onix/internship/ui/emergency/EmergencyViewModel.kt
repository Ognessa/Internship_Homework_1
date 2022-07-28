package com.onix.internship.ui.emergency

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.objects.DataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmergencyViewModel(private val dataStore: DataStore) : BaseViewModel(){
    fun clearDB(){
        launch {
            withContext(Dispatchers.IO){
                dataStore.clearDB()
                showMsgError("Cleared")
            }
        }
    }
}
package com.onix.internship.ui.emergency

import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.objects.DataStore
import com.onix.internship.ui.clearDialog.ClearModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmergencyViewModel(private val dataStore: DataStore, private val model: ClearModel) : BaseViewModel(){
    private val _showDialog = SingleLiveEvent<Unit>()
    val showDialog: LiveData<Unit> = _showDialog

    init {
        model.clear.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                clearDB()
            }
        })
    }

    private fun clearDB(){
        launch {
            withContext(Dispatchers.IO){
                if(model.clear.get()){
                    dataStore.clearDB()
                    showMsgError("Cleared")
                    model.clear.set(false)
                }
            }
        }
    }

    fun navigate(){
        _showDialog.postValue(Unit)
    }
}
package com.onix.internship.ui.advancedSearch

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.ui.tabMenu.SearchModel

class AdvancedSearchViewModel(
    private val searchModel: SearchModel
) : BaseViewModel(){

    private val model = AdvancedSearchModel()

    fun clearMap(){
        model.data.clear()
    }

    private fun clearEmptyValues(){
        model.data.forEach {
            if(it.value.isEmpty()) model.data.remove(it.key)
        }
        if(model.data["cnt"] == "CHOOSE"){
            model.data.remove("cnt")
        }
    }

    fun updateGenus(genus : String){
        model.data["gen"] = genus
    }

    fun updateSubspecies(subspecies : String){
        model.data["ssp"] = subspecies
    }

    fun updateBackgroundSpecies(backgroundSpecies : String){
        model.data["also"] = backgroundSpecies
    }

    fun updateSoundType(soundType : String){
        model.data["type"] = soundType
    }

    fun updateLocation(location : String){
        model.data["loc"] = location
    }

    fun updateCountry(country : String){
        model.data["cnt"] = country
    }

    fun updateRemarks(remarks : String){
        model.data["rmk"] = remarks
    }

    fun updateRecordist(recordist : String){
        model.data["rec"] = recordist
    }

    fun startSearch(){
        clearEmptyValues()
        searchModel.advancedSearch.postValue(model.data)
    }
}
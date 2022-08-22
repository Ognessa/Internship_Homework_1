package com.onix.internship.ui.advancedSearch

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.ui.tabMenu.SearchModel

class AdvancedSearchViewModel(
    private val searchModel: SearchModel
) : BaseViewModel(){

    val model = AdvancedSearchModel()

    fun startSearch(){
        val query = model.createQuery()
        searchModel.search.postValue(query)
    }
}
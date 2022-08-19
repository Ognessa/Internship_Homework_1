package com.onix.internship.ui.simpleSearch

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.ui.tabMenu.SearchModel

class SimpleSearchViewModel(
    private val searchModel: SearchModel
) : BaseViewModel(){

    fun startSearch(query : String){
        searchModel.search.postValue(query)
    }

}
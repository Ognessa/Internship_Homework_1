package com.onix.internship.ui.splash

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.parser.DictionaryXmlParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SplashViewModel(dictionaryXmlParser: DictionaryXmlParser) : BaseViewModel() {

    val initEvent = SingleLiveEvent<Boolean>()

    init {
        onLoading(true)
        launch{
            withContext(Dispatchers.IO){
                dictionaryXmlParser.parseAllDicts()
                initEvent.postValue(true)
            }
        }
    }

}
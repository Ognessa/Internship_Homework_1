package com.onix.internship.ui.splash

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.lifecycle.SingleLiveEvent
import com.onix.internship.objects.Scenario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SplashViewModel(scenario: Scenario) : BaseViewModel() {

    val initEvent = SingleLiveEvent<Boolean>()

    init {
        onLoading(true)
        launch {
            delay(1000)
            withContext(Dispatchers.IO){
                scenario.parseScenario()
                initEvent.postValue(true)
            }
        }
    }

}
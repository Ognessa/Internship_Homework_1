package com.onix.internship.ui.tabMenu

import androidx.databinding.ObservableBoolean
import com.onix.internship.arch.lifecycle.SingleLiveEvent

class SearchModel {
    val simpleSearch = SingleLiveEvent<String>()
    val advancedSearch = SingleLiveEvent<Map<String, String>>()
    val loading = ObservableBoolean(false)
    val navigateToSoundsList = SingleLiveEvent<Unit>()
}
package com.onix.internship.ui.tabMenu

import androidx.lifecycle.viewModelScope
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.mapper.onFailure
import com.onix.internship.arch.mapper.onSuccess
import com.onix.internship.data.mappers.ApiDataMapper
import com.onix.internship.data.repository.ApiDataRepository
import com.onix.internship.retrofit.Network
import kotlinx.coroutines.launch

class TabMenuViewModel(
    val searchModel: SearchModel,
    private val network: Network,
    private val apiDataRepository: ApiDataRepository,
    private val apiDataMapper: ApiDataMapper
) : BaseViewModel() {

    fun search(query: String) {
        viewModelScope.launch {
            try {
                searchModel.loading.set(true)
                network.makeSearch(query)
                    .onSuccess {
                        apiDataRepository.apiData = apiDataMapper.map(it)
                        searchModel.loading.set(false)
                        searchModel.navigateToSoundsList.postValue(Unit)
                    }
                    .onFailure {
                        searchModel.loading.set(false)
                        showSnack(it.message)
                    }
            } catch (e: Exception) {
                searchModel.loading.set(false)
                showSnack("Check your network connection")
            }
        }
    }

}
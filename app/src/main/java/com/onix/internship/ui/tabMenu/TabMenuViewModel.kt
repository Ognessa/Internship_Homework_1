package com.onix.internship.ui.tabMenu

import androidx.lifecycle.viewModelScope
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.arch.mapper.Either
import com.onix.internship.arch.mapper.onFailure
import com.onix.internship.arch.mapper.onSuccess
import com.onix.internship.data.mappers.ApiDataMapper
import com.onix.internship.data.repository.ApiDataRepository
import com.onix.internship.objects.apiObjects.ApiData
import com.onix.internship.retrofit.NetworkService
import kotlinx.coroutines.launch

class TabMenuViewModel(
    val searchModel: SearchModel,
    private val networkService: NetworkService,
    private val apiDataRepository: ApiDataRepository,
    private val apiDataMapper: ApiDataMapper
) : BaseViewModel() {

    fun simpleSearch(simpleQuery: String) {
        viewModelScope.launch {
            search(simpleQuery)
        }
    }

    fun advancedSearch(advancedQuery: Map<String, String>) {
        viewModelScope.launch {
            val query = advancedQuery.entries
                .joinToString(separator = " ")
                .replace("=", ":")
            search(query)
        }
    }

    private suspend fun search(query: String) {
        try {
            searchModel.loading.set(true)
            makeSearch(query)
                .onSuccess {
                    apiDataRepository.apiData = apiDataMapper.map(it)
                    searchModel.loading.set(false)
                    searchModel.navigateToSoundsList.postValue(Unit)
                }
                .onFailure {
                    searchModel.loading.set(false)
                    showMsgError(it.message)
                }
        } catch (e: Exception) {
            searchModel.loading.set(false)
            showMsgError("Check your network connection")
        }
    }

    private suspend fun makeSearch(query: String): Either<ApiData> {
        return try {
            val response = networkService.simpleSearch(query)
            val body = response.body()

            if (!response.isSuccessful || body == null) {
                Either.failure(Throwable("Request error"))
            } else {
                Either.success(body)
            }
        } catch (e: Error) {
            Either.failure(e)
        }
    }

}
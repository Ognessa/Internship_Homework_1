package com.onix.internship.ui.memeList

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.data.repository.MemeRepository
import com.onix.internship.objects.local.MemeData
import kotlinx.coroutines.flow.Flow

private const val ITEMS_PER_PAGE = 8

class MemeListViewModel(
    private val memeRepository: MemeRepository
) : BaseViewModel(){

    val items: Flow<PagingData<MemeData>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { memeRepository.memesPagingSource() }
    )
        .flow
        .cachedIn(viewModelScope)
}
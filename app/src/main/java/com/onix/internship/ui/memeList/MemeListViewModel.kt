package com.onix.internship.ui.memeList

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.data.repository.MemeRepository
import com.onix.internship.objects.local.MemeData
import kotlinx.coroutines.flow.Flow

private const val ITEMS_PER_PAGE = 8

class MemeListViewModel(
    private val memeRepository: MemeRepository
) : BaseViewModel(){

    var items: Flow<PagingData<MemeData>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { memeRepository.memesPagingSource() }
    )
        .flow
        .cachedIn(viewModelScope)

    fun updateFilter(filter : String){
        memeRepository.filter = filter
    }

    fun filterData(data : PagingData<MemeData>): PagingData<MemeData> {
        return if(memeRepository.filter == "All"){
            data
        } else{
            data.filter {
                val tags = it.tags.lowercase()
                val filter = memeRepository.filter.lowercase()
                tags.contains(filter)
            }
        }
    }
}
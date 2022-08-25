package com.onix.internship.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.onix.internship.arch.mapper.onFailure
import com.onix.internship.arch.mapper.onSuccess
import com.onix.internship.data.mappers.ApiPageMapper
import com.onix.internship.objects.local.MemeData
import kotlin.math.max

private const val STARTING_KEY = 1

class MemesPagingSource(
    private val network: Network,
    private val mapper: ApiPageMapper
) : PagingSource<Int, MemeData>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MemeData> {

        val page = params.key ?: STARTING_KEY

        var data = listOf<MemeData>()

        network.searchMemes(page)
            .onSuccess{
                data = mapper.map(it).data
            }
            .onFailure{

            }

        return LoadResult.Page(
            data = data,
            prevKey = if(page != STARTING_KEY) page - 1
                    else null,
            nextKey = page + 1)
    }

    override fun getRefreshKey(state: PagingState<Int, MemeData>): Int? {
        val position = state.anchorPosition ?: return null
        val article = state.closestItemToPosition(position) ?: return null
        return ensureValidKey(key = article.id - (state.config.pageSize / 8))

    }

    /**
     * Makes sure the paging key is never less than [STARTING_KEY]
     */
    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}
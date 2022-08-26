package com.onix.internship.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.onix.internship.arch.mapper.onFailure
import com.onix.internship.arch.mapper.onSuccess
import com.onix.internship.objects.local.MemeData

private const val STARTING_KEY = 1

class MemesPagingSource(
    private val network: Network
) : PagingSource<Int, MemeData>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MemeData> {

        val page = params.key ?: STARTING_KEY

        var data = listOf<MemeData>()

        network.searchMemes(page)
            .onSuccess{
                data = it
            }
            .onFailure{}

        Log.d("DEBUG", data.toString())

        return LoadResult.Page(
            data = data,
            prevKey = if(page != STARTING_KEY) page - 1
                    else null,
            nextKey = page + 1)
    }

    override fun getRefreshKey(state: PagingState<Int, MemeData>): Int? {
        return null
    }
}
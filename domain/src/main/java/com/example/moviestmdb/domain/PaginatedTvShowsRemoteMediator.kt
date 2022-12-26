package com.example.moviestmdb.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.moviestmdb.TvShow
import com.example.moviestmdb.core.data.tv_shows.TvShowsStore

@OptIn(ExperimentalPagingApi::class)
class PaginatedTvShowsRemoteMediator(
    private val tvShowsStore: TvShowsStore,
    private val fetch: suspend (page: Int) -> Unit
) : RemoteMediator<Int, TvShow>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TvShow>
    ): MediatorResult {
        val nextPage = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastPage = tvShowsStore.getLastPage()
                lastPage + 1
            }
        }
        return try {
            fetch(nextPage)
            MediatorResult.Success(endOfPaginationReached = false)
        } catch (t: Throwable) {
            MediatorResult.Error(t)
        }
    }
}
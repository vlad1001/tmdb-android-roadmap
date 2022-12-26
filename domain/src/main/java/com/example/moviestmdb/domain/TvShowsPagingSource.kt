package com.example.moviestmdb.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviestmdb.TvShow
import com.example.moviestmdb.core.data.tv_shows.TvShowsStore
import timber.log.Timber
import java.io.IOException

class TvShowsPagingSource(
    private val TvShowsStore: TvShowsStore,
) : PagingSource<Int, TvShow>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        return try {
            val pageNumber = params.key ?: 1

            val tvShows = TvShowsStore.getTvShowsForPage(pageNumber) ?: emptyList()

            val prevKey = if (pageNumber >= 1) pageNumber - 1 else null
            val nextKey = if (tvShows.isNotEmpty()) pageNumber + 1 else null

            LoadResult.Page(
                data = tvShows,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

}

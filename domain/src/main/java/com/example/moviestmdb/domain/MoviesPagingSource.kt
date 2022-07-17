package com.example.moviestmdb.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviestmdb.Movie
import com.example.moviestmdb.core.data.movies.MoviesStore
import timber.log.Timber
import java.io.IOException

class MoviesPagingSource(
    val moviesStore: MoviesStore
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val pageNumber = params.key ?: 1

            val movies = moviesStore.getMoviesForPage(pageNumber) ?: emptyList()

            val prevKey = if (pageNumber >= 1) pageNumber - 1 else null
            val nextKey = if (movies.isNotEmpty()) pageNumber + 1 else null

            LoadResult.Page(
                data = movies,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

}

package com.example.moviestmdb.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviestmdb.Movie
import com.example.moviestmdb.core.data.movies.MoviesRepository
import com.example.moviestmdb.core.result.successOr
import com.example.moviestmdb.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.io.IOException

class DiscoverMoviesPagingSource(
    private val moviesRepository: MoviesRepository,
    private val filters: Map<String, String> = emptyMap(),
    private val dispatchers: AppCoroutineDispatchers
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPageNumber = params.key ?: 1

            val result = moviesRepository.discover(nextPageNumber, filters)
                .flowOn(dispatchers.io)
                .first()

            val movies = result.successOr(null)

            val prevKey = null
            val nextKey =
                if (movies != null && movies.movieList.isNotEmpty()) nextPageNumber + 1 else null

            LoadResult.Page(
                data = movies?.movieList ?: emptyList(),
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}
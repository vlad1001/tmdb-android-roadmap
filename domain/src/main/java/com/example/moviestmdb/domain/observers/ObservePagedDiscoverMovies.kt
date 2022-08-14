package com.example.moviestmdb.domain.observers

import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviestmdb.Movie
import com.example.moviestmdb.core.data.movies.MoviesRepository
import com.example.moviestmdb.core.util.AppCoroutineDispatchers
import com.example.moviestmdb.domain.DiscoverMoviesPagingSource
import com.example.moviestmdb.domain.PagingInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePagedDiscoverMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val dispatchers: AppCoroutineDispatchers
) : PagingInteractor<ObservePagedDiscoverMovies.Params, Movie>() {
    override fun createObservable(
        params: Params
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = params.pagingConfig,
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    private val pagingSourceFactory = InvalidatingPagingSourceFactory(::createPagingSource)

    private fun createPagingSource(): DiscoverMoviesPagingSource {
        return DiscoverMoviesPagingSource(
            moviesRepository = moviesRepository,
            filters = emptyMap(),
            dispatchers = dispatchers
        )
    }

    data class Params(
        override val pagingConfig: PagingConfig,
    ) : Parameters<Movie>
}
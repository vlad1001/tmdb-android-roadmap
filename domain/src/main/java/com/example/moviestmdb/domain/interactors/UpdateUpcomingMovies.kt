package com.example.moviestmdb.domain.interactors

import com.example.moviestmdb.MovieResponse
import com.example.moviestmdb.core.data.movies.MoviesRepository
import com.example.moviestmdb.core.data.movies.MoviesStore
import com.example.moviestmdb.core.di.Upcoming
import com.example.moviestmdb.core.result.Result
import com.example.moviestmdb.core.util.AppCoroutineDispatchers
import com.example.moviestmdb.domain.FlowInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

class UpdateUpcomingMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    @Upcoming val upcomingStore: MoviesStore,
    private val dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<UpdateUpcomingMovies.Params, MovieResponse>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<MovieResponse>> {
        val page = when {
            params.page >= 1 -> params.page
            params.page == Page.NEXT_PAGE -> {
                val lastPage = upcomingStore.getLastPage()
                lastPage + 1
            }
            else -> 1
        }

        return moviesRepository.getUpcomingMovies(page)
            .onEach { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    is Result.Success -> moviesRepository.saveUpcomingMovies(
                        result.data.page,
                        result.data.movieList
                    )
                }
            }
    }

    data class Params(val page: Int)

    object Page {
        const val NEXT_PAGE = -1
        const val REFRESH = -2
    }
}
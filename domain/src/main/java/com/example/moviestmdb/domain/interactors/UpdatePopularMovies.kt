package com.example.moviestmdb.domain.interactors

import com.example.moviestmdb.MovieResponse
import com.example.moviestmdb.core.data.movies.MoviesRepository
import com.example.moviestmdb.core.result.Result
import com.example.moviestmdb.core.util.AppCoroutineDispatchers
import com.example.moviestmdb.domain.FlowInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

class UpdatePopularMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<UpdatePopularMovies.Params, MovieResponse>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<MovieResponse>> {
        return moviesRepository.getPopularMovies(params.page)
            .onEach { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    is Result.Success -> moviesRepository.savePopularMovies(
                        result.data.page,
                        result.data.movieList
                    )

                }
            }
    }

    data class Params(val page: Int)
}
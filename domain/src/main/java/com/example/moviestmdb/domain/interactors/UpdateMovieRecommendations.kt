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

class UpdateMovieRecommendations @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<UpdateMovieRecommendations.Params, MovieResponse>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<MovieResponse>> {
        return moviesRepository.getMovieRecommendations(params.movieId)
            .onEach { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    is Result.Success -> moviesRepository.saveMovieRecommendations(
                        movieId = params.movieId,
                        movies = result.data.movieList
                    )
                }

            }
    }

    data class Params(val movieId: Int)
}
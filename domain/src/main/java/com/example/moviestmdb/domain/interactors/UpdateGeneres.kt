package com.example.moviestmdb.domain.interactors

import com.example.moviestmdb.GenereResponse
import com.example.moviestmdb.core.data.movies.MoviesRepository
import com.example.moviestmdb.core.result.Result
import com.example.moviestmdb.core.util.AppCoroutineDispatchers
import com.example.moviestmdb.domain.FlowInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

class UpdateGeneres @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<Unit, GenereResponse>(dispatchers.io) {

    override suspend fun doWork(params: Unit): Flow<Result<GenereResponse>> {
        return moviesRepository.getGeneres()
            .onEach { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    is Result.Success -> moviesRepository.saveGeneres(
                        result.data.generes
                    )
                }
            }
    }

//    data class Params()
}
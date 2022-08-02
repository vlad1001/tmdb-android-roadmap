package com.example.moviestmdb.domain.observers

import com.example.moviestmdb.Genere
import com.example.moviestmdb.core.data.movies.MoviesRepository
import com.example.moviestmdb.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveGeneres @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : SubjectInteractor<Unit, List<Genere>>() {

    override fun createObservable(params: Unit): Flow<List<Genere>> {
        return moviesRepository.observeGeneres()
    }
}
package com.example.moviestmdb.domain.observers

import com.example.moviestmdb.Movie
import com.example.moviestmdb.core.data.movies.MoviesRepository
import com.example.moviestmdb.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObservePopularMovies @Inject constructor(
    private val moviesRepository: MoviesRepository
) : SubjectInteractor<ObservePopularMovies.Params, List<Movie>>() {

    override fun createObservable(params: Params): Flow<List<Movie>> {
        return moviesRepository.observePopularMovies()
            .map { list ->  list.flatMap { it.value } }
    }

    data class Params(val page: Int)
}
package com.example.moviestmdb.domain.observers

import com.example.moviestmdb.Movie
import com.example.moviestmdb.core.data.movies.MoviesRepository
import com.example.moviestmdb.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveUpcomingMovies @Inject constructor(
    private val moviesRepository: MoviesRepository
) : SubjectInteractor<ObserveUpcomingMovies.Params, List<Movie>>() {

    override fun createObservable(params: Params): Flow<List<Movie>> {
        return moviesRepository.observeUpcomingMovies()
            .map { list ->  list.flatMap { it.value } }
    }

    data class Params(val page: Int)
}
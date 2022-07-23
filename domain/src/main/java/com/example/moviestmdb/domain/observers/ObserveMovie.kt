package com.example.moviestmdb.domain.observers

import com.example.moviestmdb.Movie
import com.example.moviestmdb.core.data.movies.MoviesRepository
import com.example.moviestmdb.core.data.movies.MoviesStore
import com.example.moviestmdb.core.di.NowPlaying
import com.example.moviestmdb.core.di.Popular
import com.example.moviestmdb.core.di.TopRated
import com.example.moviestmdb.core.di.Upcoming
import com.example.moviestmdb.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveMovie @Inject constructor(
    private val moviesRepository: MoviesRepository,
    @Popular private val popularStore: MoviesStore,
    @TopRated private val topRatedStore: MoviesStore,
    @Upcoming private val upcomingStore: MoviesStore,
    @NowPlaying private val nowPlayingStore: MoviesStore,
) : SubjectInteractor<ObserveMovie.Params, Movie>() {

    override fun createObservable(params: Params): Flow<Movie> {
        return combine(
            popularStore.observeMovies(),
            topRatedStore.observeMovies(),
            upcomingStore.observeMovies(),
            nowPlayingStore.observeMovies(),
        ) { popular, topRated, upcoming, nowPlaying ->
            val list = mutableListOf<Movie>()
            list.addAll(popular)
            list.addAll(topRated)
            list.addAll(upcoming)
            list.addAll(nowPlaying)
            list
        }
            .map {
                it.first { it.id == params.movieId }
            }
    }

    data class Params(val movieId: Int)

}
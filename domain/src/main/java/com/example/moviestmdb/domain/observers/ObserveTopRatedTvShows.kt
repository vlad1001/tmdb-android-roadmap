package com.example.moviestmdb.domain.observers

import com.example.moviestmdb.Movie
import com.example.moviestmdb.TvShow
import com.example.moviestmdb.core.data.movies.MoviesRepository
import com.example.moviestmdb.core.data.tv_shows.TvShowsRepository
import com.example.moviestmdb.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveTopRatedTvShows @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
) : SubjectInteractor<ObserveTopRatedTvShows.Params, List<TvShow>>() {

    override fun createObservable(params: Params): Flow<List<TvShow>> {
        return tvShowsRepository.observeTopRatedTvShows()
            .map { list -> list.flatMap { it.value } }
    }

    data class Params(val page: Int)
}
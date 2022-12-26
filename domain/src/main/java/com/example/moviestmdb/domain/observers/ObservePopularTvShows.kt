package com.example.moviestmdb.domain.observers

import com.example.moviestmdb.TvShow
import com.example.moviestmdb.core.data.tv_shows.TvShowsRepository
import com.example.moviestmdb.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObservePopularTvShows @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
) : SubjectInteractor<ObservePopularTvShows.Params, List<TvShow>>() {

    override fun createObservable(params: Params): Flow<List<TvShow>> {
        return tvShowsRepository.observePopularTvShows()
            .map { list -> list.flatMap { it.value } }
    }

    data class Params(val page: Int)
}
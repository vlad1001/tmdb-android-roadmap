package com.example.moviestmdb.domain.observers

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviestmdb.TvShow
import com.example.moviestmdb.core.data.tv_shows.TvShowsStore
import com.example.moviestmdb.core.di.PopularTvShows
import com.example.moviestmdb.domain.PagingInteractor
import com.example.moviestmdb.domain.interactors.UpdatePopularTvShows
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ObservePagedTopRatedTvShows @Inject constructor(
    @PopularTvShows private val popularTvShowStore: TvShowsStore,
    private val updatePopularTvShows: UpdatePopularTvShows,
) : PagingInteractor<ObservePagedTopRatedTvShows.Params, TvShow>() {

    override fun createObservable(params: Params): Flow<PagingData<TvShow>> {
        TODO("Not yet implemented")
    }

    data class Params(
        override val pagingConfig: PagingConfig,
    ) : Parameters<TvShow>
}
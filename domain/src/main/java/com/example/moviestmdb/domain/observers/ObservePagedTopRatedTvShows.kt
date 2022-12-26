package com.example.moviestmdb.domain.observers

import androidx.paging.*
import com.example.moviestmdb.TvShow
import com.example.moviestmdb.core.data.tv_shows.TvShowsStore
import com.example.moviestmdb.core.di.TopRatedTvShows
import com.example.moviestmdb.domain.PaginatedTvShowsRemoteMediator
import com.example.moviestmdb.domain.PagingInteractor
import com.example.moviestmdb.domain.TvShowsPagingSource
import com.example.moviestmdb.domain.interactors.UpdateTopRatedTvShows
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ObservePagedTopRatedTvShows @Inject constructor(
    @TopRatedTvShows private val topRatedTvShowStore: TvShowsStore,
    private val updateTopRatedTvShows: UpdateTopRatedTvShows,
) : PagingInteractor<ObservePagedTopRatedTvShows.Params, TvShow>() {

    override fun createObservable(params: Params): Flow<PagingData<TvShow>> {
        return Pager(
            config = params.pagingConfig,
            remoteMediator = PaginatedTvShowsRemoteMediator(tvShowsStore = topRatedTvShowStore) { page ->
                updateTopRatedTvShows.executeSync(UpdateTopRatedTvShows.Params(page))
                pagingSourceFactory.invalidate()
            },
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    private val pagingSourceFactory = InvalidatingPagingSourceFactory(::createPagingSource)

    private fun createPagingSource(): TvShowsPagingSource {
        return TvShowsPagingSource(topRatedTvShowStore)
    }

    data class Params(
        override val pagingConfig: PagingConfig,
    ) : Parameters<TvShow>
}
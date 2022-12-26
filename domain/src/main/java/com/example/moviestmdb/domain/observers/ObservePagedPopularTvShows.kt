package com.example.moviestmdb.domain.observers

import androidx.paging.*
import com.example.moviestmdb.TvShow
import com.example.moviestmdb.core.data.tv_shows.TvShowsStore
import com.example.moviestmdb.core.di.PopularTvShows
import com.example.moviestmdb.domain.*
import com.example.moviestmdb.domain.interactors.UpdatePopularMovies
import com.example.moviestmdb.domain.interactors.UpdatePopularTvShows
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ObservePagedPopularTvShows @Inject constructor(
    @PopularTvShows private val popularTvShowsStore: TvShowsStore,
    private val updatePopularTvShows: UpdatePopularTvShows,
) : PagingInteractor<ObservePagedPopularTvShows.Params, TvShow>() {

    override fun createObservable(
        params: Params
    ): Flow<PagingData<TvShow>> {
        return Pager(
            config = params.pagingConfig,
            remoteMediator = PaginatedTvShowsRemoteMediator(tvShowsStore = popularTvShowsStore) { page ->
                updatePopularTvShows.executeSync(UpdatePopularTvShows.Params(page))
                pagingSourceFactory.invalidate()
            },
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    private val pagingSourceFactory = InvalidatingPagingSourceFactory(::createPagingSource)

    private fun createPagingSource(): TvShowsPagingSource {
        return TvShowsPagingSource(popularTvShowsStore)
    }

    data class Params(
        override val pagingConfig: PagingConfig,
    ) : Parameters<TvShow>
}
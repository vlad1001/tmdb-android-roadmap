package com.example.moviestmdb.domain.interactors

import com.example.moviestmdb.TvShowResponse
import com.example.moviestmdb.core.data.tv_shows.TvShowsRepository
import com.example.moviestmdb.core.data.tv_shows.TvShowsStore
import com.example.moviestmdb.core.di.PopularTvShows
import com.example.moviestmdb.core.result.Result
import com.example.moviestmdb.core.util.AppCoroutineDispatchers
import com.example.moviestmdb.domain.FlowInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

class UpdatePopularTvShows @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
    @PopularTvShows val popularTvShowsStore: TvShowsStore,
    dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<UpdatePopularTvShows.Params, TvShowResponse>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<TvShowResponse>> {
        val page = when {
            params.page >= 1 -> params.page
            params.page == Page.NEXT_PAGE -> {
                val lastPage = popularTvShowsStore.getLastPage()
                lastPage + 1
            }
            else -> 1
        }

        return tvShowsRepository.getPopularTvShows(page)
            .onEach { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    is Result.Success -> tvShowsRepository.savePopularTvShows(
                        result.data.page,
                        result.data.tvShowList
                    )
                }
            }
    }

    data class Params(val page: Int)

    object Page {
        const val NEXT_PAGE = -1
        const val REFRESH = -2
    }
}
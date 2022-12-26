package com.example.moviestmdb.ui_tvshows.lobby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviestmdb.core.extensions.combine
import com.example.moviestmdb.core.util.AppCoroutineDispatchers
import com.example.moviestmdb.core.util.ObservableLoadingCounter
import com.example.moviestmdb.core.util.UiMessageManager
import com.example.moviestmdb.core.util.collectStatus
import com.example.moviestmdb.domain.interactors.*
import com.example.moviestmdb.domain.observers.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowsLobbyViewModel @Inject constructor(
    private val updatePopularTvShows: UpdatePopularTvShows,
    private val updateTopRatedTvShows: UpdateTopRatedTvShows,
    observePopularTvShows: ObservePopularTvShows,
    observeTopRatedTvShows: ObserveTopRatedTvShows,
    private val dispatchers: AppCoroutineDispatchers,
) : ViewModel() {

    private val popularLoadingState = ObservableLoadingCounter()
    private val topRatedLoadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    init {
        observePopularTvShows(ObservePopularTvShows.Params(1))
        observeTopRatedTvShows(ObserveTopRatedTvShows.Params(1))

        refresh()
    }

    val state: StateFlow<TvShowsLobbyViewState> = combine(
        popularLoadingState.observable,
        topRatedLoadingState.observable,
        observePopularTvShows.flow,
        observeTopRatedTvShows.flow,
        uiMessageManager.message,
    ) { popularRefreshing, topRatedRefreshing,
        popularTvShows, topRatedTvShows, message ->
        TvShowsLobbyViewState(
            popularRefreshing = popularRefreshing,
            topRatedRefreshing = topRatedRefreshing,
            popularTvShows = popularTvShows,
            topRatedTvShows = topRatedTvShows,
            message = message
        )
    }.stateIn(
        scope = viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        TvShowsLobbyViewState.Empty
    )


    fun refresh() {
        viewModelScope.launch(dispatchers.io) {
            updatePopularTvShows(UpdatePopularTvShows.Params(UpdatePopularTvShows.Page.REFRESH))
                .collectStatus(
                    popularLoadingState,
                    uiMessageManager
                )
        }

        viewModelScope.launch(dispatchers.io) {
            updateTopRatedTvShows(UpdateTopRatedTvShows.Params(UpdateTopRatedTvShows.Page.REFRESH))
                .collectStatus(
                    topRatedLoadingState,
                    uiMessageManager
                )
        }
    }

    fun clearMessage(id: Long) {
        viewModelScope.launch {
            uiMessageManager.clearMessage(id)
        }
    }
}
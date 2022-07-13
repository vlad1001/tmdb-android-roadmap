package com.example.moviestmdb.ui_movies.lobby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviestmdb.core.extensions.combine
import com.example.moviestmdb.core.util.AppCoroutineDispatchers
import com.example.moviestmdb.core.util.ObservableLoadingCounter
import com.example.moviestmdb.core.util.UiMessageManager
import com.example.moviestmdb.core.util.collectStatus
import com.example.moviestmdb.domain.interactors.UpdateNowPlayingMovies
import com.example.moviestmdb.domain.interactors.UpdatePopularMovies
import com.example.moviestmdb.domain.interactors.UpdateTopRatedMovies
import com.example.moviestmdb.domain.interactors.UpdateUpcomingMovies
import com.example.moviestmdb.domain.observers.ObserveNowPlayingMovies
import com.example.moviestmdb.domain.observers.ObservePopularMovies
import com.example.moviestmdb.domain.observers.ObserveTopRatedMovies
import com.example.moviestmdb.domain.observers.ObserveUpcomingMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieLobbyViewModel @Inject constructor(
    private val updatePopularMovies: UpdatePopularMovies,
    private val updateUpcomingMovies: UpdateUpcomingMovies,
    private val updateNowPlayingMovies: UpdateNowPlayingMovies,
    private val updateTopRatedMovies: UpdateTopRatedMovies,
    observePopularMovies: ObservePopularMovies,
    observeUpcomingMovies: ObserveUpcomingMovies,
    observeNowPlayingMovies: ObserveNowPlayingMovies,
    observeTopRatedMovies: ObserveTopRatedMovies,
    private val dispatchers: AppCoroutineDispatchers,
) : ViewModel() {


    private val popularLoadingState = ObservableLoadingCounter()
    private val topRatedLoadingState = ObservableLoadingCounter()
    private val upcomingLoadingState = ObservableLoadingCounter()
    private val nowPlayingLoadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    init {
        observePopularMovies(ObservePopularMovies.Params(1))
        observeUpcomingMovies(ObserveUpcomingMovies.Params(1))
        observeNowPlayingMovies(ObserveNowPlayingMovies.Params(1))
        observeTopRatedMovies(ObserveTopRatedMovies.Params(1))

        refresh()
    }

    val state: StateFlow<LobbyViewState> = combine(
        popularLoadingState.observable,
        topRatedLoadingState.observable,
        upcomingLoadingState.observable,
        nowPlayingLoadingState.observable,
        observePopularMovies.flow,
        observeNowPlayingMovies.flow,
        observeTopRatedMovies.flow,
        observeUpcomingMovies.flow,
        uiMessageManager.message,
    ) { popularRefreshing, topRatedRefreshing, upcomingRefreshing, nowPlayingRefreshing,
        popularMovies, nowPlayingMovies, topRatedMovies, upcomingMovies, message ->
        LobbyViewState(
            popularRefreshing = popularRefreshing,
            topRatedRefreshing = topRatedRefreshing,
            upcomingRefreshing = upcomingRefreshing,
            nowPlayingRefreshing = nowPlayingRefreshing,
            popularMovies = popularMovies,
            topRatedMovies = topRatedMovies,
            upcomingMovies = upcomingMovies,
            nowPlayingMovies = nowPlayingMovies,
            message = message
        )
    }.stateIn(
        scope = viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        LobbyViewState.Empty
    )


    fun refresh() {
        viewModelScope.launch(dispatchers.io) {
            updatePopularMovies(UpdatePopularMovies.Params(UpdatePopularMovies.Page.REFRESH))
                .collectStatus(
                    popularLoadingState,
                    uiMessageManager
                )
        }

        viewModelScope.launch(dispatchers.io) {
            updateUpcomingMovies(UpdateUpcomingMovies.Params(UpdateUpcomingMovies.Page.REFRESH))
                .collectStatus(
                    upcomingLoadingState,
                    uiMessageManager
                )
        }

        viewModelScope.launch(dispatchers.io) {
            updateNowPlayingMovies(UpdateNowPlayingMovies.Params(UpdateNowPlayingMovies.Page.REFRESH))
                .collectStatus(
                    nowPlayingLoadingState,
                    uiMessageManager
                )
        }

        viewModelScope.launch(dispatchers.io) {
            updateTopRatedMovies(UpdateTopRatedMovies.Params(UpdateTopRatedMovies.Page.REFRESH))
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
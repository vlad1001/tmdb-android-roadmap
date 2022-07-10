package com.example.moviestmdb.ui_movies.lobby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviestmdb.core.extensions.combine
import com.example.moviestmdb.core.util.AppCoroutineDispatchers
import com.example.moviestmdb.core.util.ObservableLoadingCounter
import com.example.moviestmdb.core.util.UiMessageManager
import com.example.moviestmdb.core.util.collectStatus
import com.example.moviestmdb.domain.interactors.UpdatePopularMovies
import com.example.moviestmdb.domain.observers.ObservePopularMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MovieLobbyViewModel @Inject constructor(
    private val updatePopularMovies: UpdatePopularMovies,
    observePopularMovies: ObservePopularMovies,
    private val dispatchers: AppCoroutineDispatchers,
) : ViewModel() {


    private val popularLoadingState = ObservableLoadingCounter()
    private val topRatedLoadingState = ObservableLoadingCounter()
    private val upcomingLoadingState = ObservableLoadingCounter()
    private val nowPlayingLoadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    init {
        observePopularMovies(ObservePopularMovies.Params(1))

        viewModelScope.launch {
            observePopularMovies.flow.collect { list ->
                Timber.i("### ${list.size}")
            }
        }
    }

    val state: StateFlow<LobbyViewState> = combine(
        popularLoadingState.observable,
        topRatedLoadingState.observable,
        upcomingLoadingState.observable,
        nowPlayingLoadingState.observable,
        observePopularMovies.flow,
        uiMessageManager.message,
    ) { popularRefreshing, topRatedRefreshing, upcomingRefreshing, nowPlayingRefreshing, popularMovies, message ->
        LobbyViewState(
            popularRefreshing = popularRefreshing,
            topRatedRefreshing = topRatedRefreshing,
            upcomingRefreshing = upcomingRefreshing,
            nowPlayingRefreshing = nowPlayingRefreshing,
            popularMovies = popularMovies,
            message = message
        )
    }.stateIn(
        scope = viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        LobbyViewState.Empty
    )


    fun refresh() {
        viewModelScope.launch(dispatchers.io) {
            updatePopularMovies(UpdatePopularMovies.Params(1)).collectStatus(
                popularLoadingState,
                uiMessageManager
            )
        }
    }
}
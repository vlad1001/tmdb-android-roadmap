package com.example.moviestmdb.ui_tvshows.lobby

import com.example.moviestmdb.TvShow
import com.example.moviestmdb.core.util.UiMessage

data class TvShowsLobbyViewState(
    val popularTvShows: List<TvShow> = emptyList(),
    val popularRefreshing: Boolean = false,
    val topRatedTvShows: List<TvShow> = emptyList(),
    val topRatedRefreshing: Boolean = false,
    val message: UiMessage? = null
) {
    val refreshing: Boolean
        get() = popularRefreshing || topRatedRefreshing

    companion object {
        val Empty = TvShowsLobbyViewState()
    }
}
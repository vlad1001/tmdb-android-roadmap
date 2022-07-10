package com.example.moviestmdb.ui_movies.lobby

import com.example.moviestmdb.Movie
import com.example.moviestmdb.core.util.UiMessage

data class LobbyViewState(
    val popularMovies: List<Movie> = emptyList(),
    val popularRefreshing: Boolean = false,
    val topRatedMovies: List<Movie> = emptyList(),
    val topRatedRefreshing: Boolean = false,
    val upcomingMovies: List<Movie> = emptyList(),
    val upcomingRefreshing: Boolean = false,
    val nowPlayingMovies: List<Movie> = emptyList(),
    val nowPlayingRefreshing: Boolean = false,
    val message: UiMessage? = null
) {
    val refreshing: Boolean
        get() = popularRefreshing || topRatedRefreshing || nowPlayingRefreshing || upcomingRefreshing

    companion object {
        val Empty = LobbyViewState()
    }
}
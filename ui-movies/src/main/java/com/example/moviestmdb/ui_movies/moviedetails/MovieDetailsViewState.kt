package com.example.moviestmdb.ui_movies.moviedetails

import com.example.moviestmdb.Cast
import com.example.moviestmdb.Movie
import com.example.moviestmdb.core.util.UiMessage

data class MovieDetailsViewState(
    val movie: Movie? = null,
    val casts: List<Cast> = emptyList(),
    val castsRefreshing: Boolean = false,
    val recommendetions: List<Movie> = emptyList(),
    val recommendetionsRefreshing: Boolean = false,
    val favouriteMovies: Set<String> = emptySet(),
    val message: UiMessage? = null
) {

    val refreshing get() = castsRefreshing || recommendetionsRefreshing

    companion object {
        val EMPTY = MovieDetailsViewState()
    }
}
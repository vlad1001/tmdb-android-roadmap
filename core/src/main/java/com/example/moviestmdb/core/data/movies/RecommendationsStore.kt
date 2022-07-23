package com.example.moviestmdb.core.data.movies

import com.example.moviestmdb.Movie
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecommendationsStore @Inject constructor() {

    private val _movieToRecommendations = MutableSharedFlow<Map<Int, List<Movie>>>(replay = 1).apply {
        tryEmit(emptyMap())
    }

    fun insert(movieId: Int, movies: List<Movie>) {
        val map = _movieToRecommendations.replayCache.first().toMutableMap()
        map[movieId] = movies
        _movieToRecommendations.tryEmit(map)
    }

    fun observeEnteries(): SharedFlow<Map<Int, List<Movie>>> =
        _movieToRecommendations.asSharedFlow()

    fun getRecommendationsForMovie(movieId: Int) =
        _movieToRecommendations.replayCache.firstOrNull()?.let { it[movieId] }
}
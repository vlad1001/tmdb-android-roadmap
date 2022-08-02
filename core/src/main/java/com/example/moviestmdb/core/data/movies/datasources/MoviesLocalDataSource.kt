package com.example.moviestmdb.core.data.movies.datasources

import com.example.moviestmdb.Genere
import com.example.moviestmdb.core.data.movies.CastsStore
import com.example.moviestmdb.core.data.movies.MoviesStore
import com.example.moviestmdb.core.data.movies.RecommendationsStore
import com.example.moviestmdb.core.di.NowPlaying
import com.example.moviestmdb.core.di.Popular
import com.example.moviestmdb.core.di.TopRated
import com.example.moviestmdb.core.di.Upcoming
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    @Popular val popularStore: MoviesStore,
    @TopRated val topRatedStore: MoviesStore,
    @Upcoming val upcomingStore: MoviesStore,
    @NowPlaying val nowPlayingStore: MoviesStore,
    val recommendationsStore: RecommendationsStore,
    val castsStore: CastsStore
) {

    private val _generes = MutableSharedFlow<List<Genere>>(replay = 1).apply {
        tryEmit(emptyList())
    }

    private val generes = _generes.asSharedFlow()

    fun saveGeneres(generes: List<Genere>) {
        _generes.tryEmit(generes)
    }

    //    fun observeGeneres(): SharedFlow<List<Genere>> = _generes.asSharedFlow()
    fun observeGeneres(): SharedFlow<List<Genere>> = generes
}
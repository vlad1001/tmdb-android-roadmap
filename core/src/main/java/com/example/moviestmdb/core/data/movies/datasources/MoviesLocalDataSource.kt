package com.example.moviestmdb.core.data.movies.datasources

import com.example.moviestmdb.Movie
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MoviesLocalDataSource() {

    private val _sharedPopularMoviesFlow = MutableSharedFlow<Map<Int, List<Movie>>>(replay = 1)
    val sharedPopularMoviesFlow = _sharedPopularMoviesFlow.asSharedFlow()

    private val _sharedNowPlayingMoviesFlow = MutableSharedFlow<Map<Int, List<Movie>>>(replay = 1)
    val sharedNowPlayingMoviesFlow = _sharedNowPlayingMoviesFlow.asSharedFlow()

    private val _sharedUpcomingMoviesFlow = MutableSharedFlow<Map<Int, List<Movie>>>(replay = 1)
    val sharedUpcomingMoviesFlow = _sharedUpcomingMoviesFlow.asSharedFlow()

    private val _sharedTopRatedMoviesFlow = MutableSharedFlow<Map<Int, List<Movie>>>(replay = 1)
    val sharedTopRatedMoviesFlow = _sharedTopRatedMoviesFlow.asSharedFlow()

//    fun getMovies(){}
//
////    fun observeMovies() : SharedFlow<List<Movie>> {
////        return local.movies
////    }

    fun savePopularMovies(page: Int, movies: List<Movie>){
        if(page == 1){
            _sharedPopularMoviesFlow.resetReplayCache()
        }
            _sharedPopularMoviesFlow.tryEmit(mapOf(page to movies))
    }

    fun saveNowPlayingMovies(page: Int, movies: List<Movie>){
        if(page == 1){
            _sharedNowPlayingMoviesFlow.resetReplayCache()
        }
        _sharedNowPlayingMoviesFlow.tryEmit(mapOf(page to movies))
    }

    fun saveUpcomingMovies(page: Int, movies: List<Movie>){
        if(page == 1){
            _sharedUpcomingMoviesFlow.resetReplayCache()
        }
        _sharedUpcomingMoviesFlow.tryEmit(mapOf(page to movies))
    }

//    fun savePopularMovies(page: Int, movies: List<Movie>){
//        if(page == 1){
//            _sharedPopularMoviesFlow.resetReplayCache()
//        }
//        _sharedPopularMoviesFlow.tryEmit(mapOf(page to movies))
//    }
}
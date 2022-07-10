package com.example.moviestmdb.core.data.movies

import com.example.moviestmdb.core.data.movies.datasources.MoviesLocalDataSource
import com.example.moviestmdb.core.data.movies.datasources.MoviesRemoteDataSource
import kotlinx.coroutines.flow.flow

class MoviesRepository (
    val remote: MoviesRemoteDataSource,
    val local: MoviesLocalDataSource
){

//    fun getMovie(page: Int) = flow {
//        var data = remote.getPopularMovies(1).first()
//    }
}
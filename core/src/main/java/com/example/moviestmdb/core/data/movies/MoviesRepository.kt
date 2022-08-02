package com.example.moviestmdb.core.data.movies

import com.example.moviestmdb.Cast
import com.example.moviestmdb.Genere
import com.example.moviestmdb.GenereResponse
import com.example.moviestmdb.Movie
import com.example.moviestmdb.core.data.movies.datasources.FirebaseDatabaseDataSource
import com.example.moviestmdb.core.data.movies.datasources.MoviesLocalDataSource
import com.example.moviestmdb.core.data.movies.datasources.MoviesRemoteDataSource
import com.example.moviestmdb.core.result.Result
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val remote: MoviesRemoteDataSource,
    private val local: MoviesLocalDataSource,
    private val firebaseDatabaseDataSource: FirebaseDatabaseDataSource
) {

    suspend fun getPopularMovies(page: Int) =
        flow {
            emit(remote.getPopularMovies(page))
        }

    fun savePopularMovies(page: Int, movies: List<Movie>) {
        local.popularStore.insert(page, movies)
    }

    fun observePopularMovies() = local.popularStore.observeEnteries()

    suspend fun getNowPlayingMovies(page: Int) =
        flow {
            emit(remote.getNowPlayingMovies(page))
        }

    fun saveNowPlayingMovies(page: Int, movies: List<Movie>) {
        local.nowPlayingStore.insert(page, movies)
    }

    fun observeNowPlayingMovies() = local.nowPlayingStore.observeEnteries()

    suspend fun getTopRatedMovies(page: Int) =
        flow {
            emit(remote.getTopRated(page))
        }

    fun saveTopRatedMovies(page: Int, movies: List<Movie>) {
        local.topRatedStore.insert(page, movies)
    }

    fun observeTopRatedMovies() = local.topRatedStore.observeEnteries()

    suspend fun getUpcomingMovies(page: Int) =
        flow {
            emit(remote.getUpcoming(page))
        }

    fun saveUpcomingMovies(page: Int, movies: List<Movie>) {
        local.upcomingStore.insert(page, movies)
    }

    fun observeUpcomingMovies() = local.upcomingStore.observeEnteries()

    suspend fun getMovieCredits(movieId: Int) =
        flow {
            emit(remote.getMovieCredits(movieId))
        }

    fun saveMovieCasts(movieId: Int, casts: List<Cast>) {
        local.castsStore.insert(movieId, casts)
    }

    suspend fun getMovieRecommendations(movieId: Int) =
        flow {
            emit(remote.getMovieRecommendations(movieId))
        }

    fun saveMovieRecommendations(movieId: Int, movies: List<Movie>) {
        local.recommendationsStore.insert(movieId.toInt(), movies)
    }

    fun addToFavourites(uid: String, movieId: Int) {
        firebaseDatabaseDataSource.addToFavourite(uid, movieId)
    }

    fun removeFromFavourite(uid: String, movieId: Int) {
        firebaseDatabaseDataSource.removeFromFavourite(uid, movieId)
    }

    fun observeFavouriteMovies() = firebaseDatabaseDataSource.observeFavouriteMovies()

    fun getGeneres() =
        flow {
            emit(remote.getGenere())
        }

    fun saveGeneres(generes: List<Genere>) = local.saveGeneres(generes)

    fun observeGeneres() = local.observeGeneres()

}
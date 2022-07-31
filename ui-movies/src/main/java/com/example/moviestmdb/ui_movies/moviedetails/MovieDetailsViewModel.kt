package com.example.moviestmdb.ui_movies.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviestmdb.core.data.login.FirebaseAuthStateUserDataSource
import com.example.moviestmdb.core.data.movies.MoviesRepository
import com.example.moviestmdb.core.data.movies.datasources.FirebaseDatabaseDataSource
import com.example.moviestmdb.core.extensions.combine
import com.example.moviestmdb.core.util.AppCoroutineDispatchers
import com.example.moviestmdb.core.util.ObservableLoadingCounter
import com.example.moviestmdb.core.util.UiMessageManager
import com.example.moviestmdb.core.util.collectStatus
import com.example.moviestmdb.domain.interactors.UpdateMovieCasts
import com.example.moviestmdb.domain.interactors.UpdateMovieRecommendations
import com.example.moviestmdb.domain.observers.ObserveCasts
import com.example.moviestmdb.domain.observers.ObserveMovie
import com.example.moviestmdb.domain.observers.ObserveRecommendetions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updateMovieCasts: UpdateMovieCasts,
    private val updateMovieRecommendations: UpdateMovieRecommendations,
    private val observeMovie: ObserveMovie,
    private val observeCasts: ObserveCasts,
    private val observeRecommendetions: ObserveRecommendetions,
    private val firebaseAuthStateUserDataSource: FirebaseAuthStateUserDataSource,
    private val moviesRepository: MoviesRepository,
    private val dispatchers: AppCoroutineDispatchers,
) : ViewModel() {

    private val movieId = savedStateHandle.get<Int>("arg_movie_id")!!

    private val castsLoadingState = ObservableLoadingCounter()
    private val recommendationsLoadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()
    private val observeFavouriteMovies = moviesRepository.observeFavouriteMovies()

    init {
        observeMovie(ObserveMovie.Params(movieId = movieId))
        observeCasts(ObserveCasts.Params(movieId = movieId))
        observeRecommendetions(ObserveRecommendetions.Params(movieId = movieId))

        refresh()
    }


    val state: StateFlow<MovieDetailsViewState> = combine(
        observeMovie.flow,
        observeCasts.flow,
        observeRecommendetions.flow,
        castsLoadingState.observable,
        recommendationsLoadingState.observable,
        observeFavouriteMovies,
        uiMessageManager.message
    ) { movie, casts, rec, castsRefreshing, recRefreshing, favouriteMovies, message ->
        MovieDetailsViewState(
            movie = movie,
            casts = casts,
            castsRefreshing = castsRefreshing,
            recommendetions = rec,
            recommendetionsRefreshing = recRefreshing,
            favouriteMovies = favouriteMovies,
            message = message
        )
    }.stateIn(
        scope = viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        initialValue = MovieDetailsViewState.EMPTY
    )

    fun refresh() {
        viewModelScope.launch(dispatchers.io) {
            updateMovieCasts(UpdateMovieCasts.Params(movieId = movieId))
                .collectStatus(
                    castsLoadingState,
                    uiMessageManager
                )

        }

        viewModelScope.launch(dispatchers.io) {
            updateMovieRecommendations(UpdateMovieRecommendations.Params(movieId = movieId))
                .collectStatus(
                    recommendationsLoadingState,
                    uiMessageManager
                )

        }
    }

    fun addToFavorites() {
        viewModelScope.launch(dispatchers.io) {
            firebaseAuthStateUserDataSource.getBasicUserInfo()
                .filterNotNull()
                .filterNot { it.getUid() == null }
                .map { user -> moviesRepository.addToFavourites(user.getUid()!!, movieId) }
                .collect {

                }
        }
    }

    fun removeFromFavorites() {
        viewModelScope.launch(dispatchers.io) {
            firebaseAuthStateUserDataSource.getBasicUserInfo()
                .filterNotNull()
                .filterNot { it.getUid() == null }
                .map { user -> moviesRepository.removeFromFavourite(user.getUid()!!, movieId) }
                .collect {

                }
        }
    }
}
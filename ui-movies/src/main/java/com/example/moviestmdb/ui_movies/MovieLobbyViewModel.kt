package com.example.moviestmdb.ui_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviestmdb.core.data.movies.MoviesRepository
import com.example.moviestmdb.domain.interactors.UpdatePopularMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieLobbyViewModel @Inject constructor(
    private val updatePopularMovies: UpdatePopularMovies
) : ViewModel() {

    init {


    }



    fun loadMoview() {
        viewModelScope.launch {
            updatePopularMovies(UpdatePopularMovies.Params(1)).collect { result ->


            }
        }
    }
}
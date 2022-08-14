package com.example.moviestmdb.ui_movies.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviestmdb.domain.observers.ObserveGeneres
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FiltersBottomSheetViewModel @Inject constructor(
    private val observeGeneres:ObserveGeneres
): ViewModel() {


    val generes = observeGeneres.flow
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        observeGeneres(Unit)
    }
}
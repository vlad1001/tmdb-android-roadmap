package com.example.moviestmdb.ui_movies.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.moviestmdb.Movie
import com.example.moviestmdb.MovieWithGenere
import com.example.moviestmdb.core.data.movies.MoviesRepository
import com.example.moviestmdb.core.util.AppCoroutineDispatchers
import com.example.moviestmdb.domain.interactors.UpdateGeneres
import com.example.moviestmdb.domain.observers.ObserveGeneres
import com.example.moviestmdb.domain.observers.ObservePagedPopularMovies
import com.example.moviestmdb.ui_movies.popular.vo.FilterChip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val pagingInteractor: ObservePagedPopularMovies,
    private val observeGeneres: ObserveGeneres,
    private val dispatchers: AppCoroutineDispatchers,
) : ViewModel() {

    private val _selectedChips = MutableStateFlow<Set<Int>>(emptySet())
    val selectedChips = _selectedChips.asStateFlow()

    private val generesAndFilters = combine(
        selectedChips,
        observeGeneres.flow
    ) { selectedIds, generes ->
        Pair(selectedIds, generes)
    }

    val pagedList: Flow<PagingData<MovieWithGenere>> =
        generesAndFilters
            .flatMapLatest { pair ->
                pagingInteractor.flow
                    .map { pagingData ->
                        if (pair.first.isEmpty()) {
                            pagingData
                        } else {
                            pagingData.filter { movie ->
                                movie.genreList.any { x -> x in pair.first }
                            }
                        }
                    }
                    .map { pagingData ->
                        pagingData.map {
                            MovieWithGenere(
                                movie = it,
                                generes = pair.second
                            )
                        }
                    }
            }.cachedIn(viewModelScope)

    val filterChips = observeGeneres.flow
        .map { generes ->
            generes.mapTo(mutableListOf()) { genere ->
                FilterChip(
                    id = genere.id,
                    text = genere.name,
                    isSelected = false
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList<FilterChip>()
        )

    init {
        pagingInteractor(ObservePagedPopularMovies.Params(PAGING_CONFIG))
        observeGeneres(Unit)

    }

    fun toggleFilter(id: Int, enabled: Boolean) {
        val set = _selectedChips.value.toMutableSet()
        val changed = if (enabled) {
            set.add(id)
        } else {
            set.remove(id)
        }

        if (changed) {
            _selectedChips.tryEmit(set)
//            _selectedChips.value = set
        }
    }

    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            initialLoadSize = 60,
        )
    }
}
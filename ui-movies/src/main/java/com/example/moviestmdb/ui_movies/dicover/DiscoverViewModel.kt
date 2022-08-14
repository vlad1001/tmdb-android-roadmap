package com.example.moviestmdb.ui_movies.dicover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.moviestmdb.MovieWithGenere
import com.example.moviestmdb.core.util.AppCoroutineDispatchers
import com.example.moviestmdb.domain.observers.ObserveGeneres
import com.example.moviestmdb.domain.observers.ObservePagedDiscoverMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val observeGeneres: ObserveGeneres,
    private val pagingInteractor: ObservePagedDiscoverMovies,
    private val dispatchers: AppCoroutineDispatchers,
) : ViewModel() {

    val pagingList: Flow<PagingData<MovieWithGenere>> =
        observeGeneres.flow.flatMapLatest { generes ->
            pagingInteractor.flow
                .map { pagingData ->
                    pagingData.map {
                        MovieWithGenere(
                            movie = it,
                            generes = generes
                        )
                    }
                }.cachedIn(viewModelScope)
        }.flowOn(dispatchers.io)

    init {
        pagingInteractor(ObservePagedDiscoverMovies.Params(pagingConfig = PAGING_CONFIG))
        observeGeneres(Unit)
    }

    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            initialLoadSize = 60,
        )
    }
}
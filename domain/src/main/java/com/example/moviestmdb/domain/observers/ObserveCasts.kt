package com.example.moviestmdb.domain.observers

import com.example.moviestmdb.Cast
import com.example.moviestmdb.core.data.movies.CastsStore
import com.example.moviestmdb.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

class ObserveCasts @Inject constructor(
    private val castsStore: CastsStore,
) : SubjectInteractor<ObserveCasts.Params, List<Cast>>() {

    override fun createObservable(params: Params): Flow<List<Cast>> {
        return castsStore.observeEnteries()
            .onEach { Timber.i("###$it") }
            .map { it[params.movieId] }
            .filterNotNull()
    }

    data class Params(val movieId: Int)
}
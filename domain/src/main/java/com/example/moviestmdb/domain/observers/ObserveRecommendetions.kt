package com.example.moviestmdb.domain.observers

import com.example.moviestmdb.Movie
import com.example.moviestmdb.core.data.movies.RecommendationsStore
import com.example.moviestmdb.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveRecommendetions @Inject constructor(
    private val recommendationsStore: RecommendationsStore,
) : SubjectInteractor<ObserveRecommendetions.Params, List<Movie>>() {

    override fun createObservable(params: Params): Flow<List<Movie>> {
        return recommendationsStore.observeEnteries()
            .map { it[params.movieId] }
            .filterNotNull()
    }

    data class Params(val movieId: Int)
}
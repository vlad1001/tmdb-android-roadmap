package com.example.moviestmdb.core.data.tv_shows

import com.example.moviestmdb.TvShow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowsStore @Inject constructor() {
    private val _tvShows = MutableSharedFlow<Map<Int, List<TvShow>>>(replay = 1)

    fun insert(page: Int, tvShows: List<TvShow>) {
        if (page == 1) {
            _tvShows.resetReplayCache()
            _tvShows.tryEmit(mapOf(page to tvShows))
        } else {
            val map = _tvShows.replayCache.first().toMutableMap()
            map[page] = tvShows
            _tvShows.tryEmit(map)
        }
    }

    fun observeEntries(): SharedFlow<Map<Int, List<TvShow>>> = _tvShows.asSharedFlow()

    fun updatePage(page: Int, tvShow: List<TvShow>) {
        val map = _tvShows.replayCache.first().toMutableMap()
        map[page] = tvShow
        _tvShows.tryEmit(map)
    }

    fun deletePage(page: Int) {
        val map = _tvShows.replayCache.first().toMutableMap()
        map.remove(page)
        _tvShows.tryEmit(map)
    }

    fun deleteAll() {
        _tvShows.resetReplayCache()
    }

    fun getLastPage(): Int {
        return _tvShows.replayCache.firstOrNull()?.let { map ->
            map.maxOf { it.key }
        } ?: 0
    }

    fun getTvShowsForPage(page:Int) = _tvShows.replayCache.firstOrNull()?.let { it[page] }

    fun observeTvShows() = _tvShows.map { it.values.flatten() }
}
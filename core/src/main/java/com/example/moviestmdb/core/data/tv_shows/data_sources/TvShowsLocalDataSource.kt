package com.example.moviestmdb.core.data.tv_shows.data_sources

import com.example.moviestmdb.core.data.tv_shows.TvShowsStore
import com.example.moviestmdb.core.di.PopularTvShows
import com.example.moviestmdb.core.di.TopRatedTvShows
import javax.inject.Inject

class TvShowsLocalDataSource @Inject constructor(
    @PopularTvShows val popularTvShowsStore: TvShowsStore,
    @TopRatedTvShows val topRatedTvShowsStore: TvShowsStore,
)
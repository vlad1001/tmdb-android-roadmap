package com.example.moviestmdb.core.di

import javax.inject.Qualifier


@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class Popular

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class TopRated

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class Upcoming

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class NowPlaying

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class ApplicationScope

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class PopularTvShows

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@MustBeDocumented
annotation class TopRatedTvShows
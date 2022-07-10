package com.example.moviestmdb

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
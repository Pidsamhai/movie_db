package com.github.psm.moviedb.utils

object Keys {
    init {
        System.loadLibrary("native-lib")
    }
    external fun TMDBApiKey(): String
}
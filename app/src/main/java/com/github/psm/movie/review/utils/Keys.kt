package com.github.psm.movie.review.utils

object Keys {
    init {
        System.loadLibrary("native-lib")
    }
    external fun TMDBApiKey(): String
}
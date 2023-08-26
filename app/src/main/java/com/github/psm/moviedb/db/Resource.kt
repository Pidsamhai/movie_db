package com.github.psm.moviedb.db

sealed class Resource<out T> {
    data object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T): Resource<T>()
    data class Error<out T>(val error: Exception, val data: T?): Resource<T>()
}
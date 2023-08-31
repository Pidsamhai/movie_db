package com.github.psm.moviedb.db

sealed class Resource<out T> {
    data object Initial: Resource<Nothing>()
    data object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T): Resource<T>()
    data class Error(val error: Exception): Resource<Nothing>()
}
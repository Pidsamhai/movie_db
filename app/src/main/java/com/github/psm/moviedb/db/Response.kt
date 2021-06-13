package com.github.psm.moviedb.db

sealed class Response<out T : Any> {
    data class Success<out T : Any>(val value: T) : Response<T>()
    data class Error<out T : Exception>(val e: T) : Response<Nothing>()
}
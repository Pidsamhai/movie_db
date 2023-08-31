package com.github.psm.moviedb.utils

import com.github.psm.moviedb.db.Resource
import kotlin.reflect.KProperty

operator fun <T> Resource<T>?.getValue(t: T?, property: KProperty<*>): T? {
    return when (this) {
        is Resource.Success -> this.data
        else -> null
    }
}

fun <T> Resource<T>.isLoading(): Boolean = this is Resource.Loading

fun <T> Resource<T>.isError(): Boolean = this is Resource.Error

fun isLoadings(vararg element: Resource<Any?>?): Boolean {
    return element.find { it is Resource.Loading } != null
}
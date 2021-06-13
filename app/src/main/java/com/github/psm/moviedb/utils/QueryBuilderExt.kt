package com.github.psm.moviedb.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import io.objectbox.query.QueryBuilder

fun <T> QueryBuilder<T>.asLiveData(): ObjectBoxLiveData<T> {
    return ObjectBoxLiveData(this)
}

fun <T> QueryBuilder<T>.asFilerLiveData(callBack: (list: List<T>) -> T): LiveData<T> {
    return ObjectBoxLiveData(this).map {
        callBack.invoke(it)
    }
}
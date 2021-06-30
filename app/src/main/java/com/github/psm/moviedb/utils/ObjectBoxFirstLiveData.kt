package com.github.psm.moviedb.utils

import androidx.lifecycle.LiveData
import io.objectbox.query.QueryBuilder
import io.objectbox.reactive.DataObserver
import io.objectbox.reactive.DataSubscription

class ObjectBoxFirstLiveData<T>(
    queryBuilder: QueryBuilder<T>
) : LiveData<T?>() {
    private var subscription: DataSubscription? = null
    private val observer = DataObserver<List<T>> { postValue(it.firstOrNull()) }
    private val query = queryBuilder.build()

    override fun onActive() {
        super.onActive()
        subscription = query.subscribe().observer(observer)
    }

    override fun onInactive() {
        super.onInactive()
        if (!hasObservers())
            subscription?.cancel()?.also {
                subscription = null
            }
    }
}
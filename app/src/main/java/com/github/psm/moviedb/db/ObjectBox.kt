package com.github.psm.moviedb.db

import android.content.Context
import com.github.psm.moviedb.db.model.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import timber.log.Timber

object ObjectBox {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context)
            .build()
        val started = AndroidObjectBrowser(store).start(context)
        Timber.i("ObjectBrowser Started: $started")
    }
}
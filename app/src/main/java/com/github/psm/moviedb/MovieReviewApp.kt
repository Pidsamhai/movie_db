package com.github.psm.moviedb

import android.app.Application
import com.github.psm.moviedb.db.ObjectBox
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MovieReviewApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
        Timber.plant(
            object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return "${element.fileName} : ${element.methodName} \t ${element.lineNumber}"
                }
            }
        )
    }
}

package com.github.psm.movie.review.utils

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

private val supportType = listOf(
    String::class.java,
    Int::class.java,
    Boolean::class.java,
    Float::class.java,
    Long::class.java
)


private class SharePreferenceLiveData<T>(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val default: T,
    private val klass: Class<T>
) : LiveData<T>() {

    init {
        if (klass !in supportType) throw Exception("Data type not supported")
    }

    private val observe = SharedPreferences.OnSharedPreferenceChangeListener { pref, key ->
        if (this.key == key) {
            setValue(pref, key)
        }
    }

    override fun onActive() {
        super.onActive()
        setValue(sharedPreferences, key)
        sharedPreferences.registerOnSharedPreferenceChangeListener(observe)
    }

    override fun onInactive() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(observe)
        super.onInactive()
    }

    @Suppress("UNCHECKED_CAST")
    private fun setValue(pref: SharedPreferences, key: String) {
        when (klass) {
            String::class.java -> {
                val value = pref.getString(key, default as String) ?: default
                postValue(value as T)
            }
            Int::class.java -> {
                val value = pref.getInt(key, default as Int)
                postValue(value as T)
            }
            Boolean::class.java -> {
                val value = pref.getBoolean(key, default as Boolean)
                postValue(value as T)
            }
            Float::class.java -> {
                val value = pref.getFloat(key, default as Float)
                postValue(value as T)
            }
            Long::class.java -> {
                val value = pref.getLong(key, default as Long)
                postValue(value as T)
            }
        }
    }
}

fun SharedPreferences.getStringLiveData(key: String, default: String): LiveData<String> =
    SharePreferenceLiveData(this, key, default, String::class.java)
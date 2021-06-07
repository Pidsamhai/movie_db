package com.github.psm.movie.review.ui.about

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.psm.movie.review.utils.getStringLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val pref: SharedPreferences
): ViewModel() {
    val language:LiveData<String> = pref.getStringLiveData("language", "en-US")

    fun saveLanguage(language: String) {
        pref.edit()
            .putString("language", language)
            .apply()
    }
}
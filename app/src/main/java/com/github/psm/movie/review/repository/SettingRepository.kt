package com.github.psm.movie.review.repository

import androidx.lifecycle.LiveData

interface SettingRepository {
    val languageCode: String
    val regionCode: String
    val languageCodeLiveData: LiveData<String>
    val regionCodeLiveData: LiveData<String>
    fun saveLanguageCode(languageCode: String)
    fun saveRegionCode(regionCode: String)
}
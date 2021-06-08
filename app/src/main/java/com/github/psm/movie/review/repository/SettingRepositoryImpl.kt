package com.github.psm.movie.review.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import com.github.psm.movie.review.utils.getStringLiveData
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val pref: SharedPreferences
) : SettingRepository {
    override val languageCode: String
        get() = pref.getString(LANGUAGE_CODE_KEY, LANGUAGE_CODE_DEFAULT) ?: LANGUAGE_CODE_DEFAULT
    override val regionCode: String
        get() = pref.getString(REGION_CODE_KEY, REGION_CODE_DEFAULT) ?: REGION_CODE_DEFAULT
    override val languageCodeLiveData: LiveData<String>
        get() = pref.getStringLiveData(LANGUAGE_CODE_KEY, LANGUAGE_CODE_DEFAULT)
    override val regionCodeLiveData: LiveData<String>
        get() = pref.getStringLiveData(REGION_CODE_KEY, REGION_CODE_DEFAULT)

    override fun saveLanguageCode(languageCode: String) {
        pref.edit {
            putString(LANGUAGE_CODE_KEY, languageCode)
        }
    }

    override fun saveRegionCode(regionCode: String) {
        pref.edit {
            putString(REGION_CODE_KEY, regionCode)
        }
    }

    companion object {
        private const val LANGUAGE_CODE_KEY = "language_code_key"
        private const val REGION_CODE_KEY = "region_code_key"
        private const val LANGUAGE_CODE_DEFAULT = "en-US"
        private const val REGION_CODE_DEFAULT = "US"
    }
}
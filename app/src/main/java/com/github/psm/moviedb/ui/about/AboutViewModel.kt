package com.github.psm.moviedb.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.psm.moviedb.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val settingRepository: SettingRepository
): ViewModel() {
    val language:LiveData<String> = settingRepository.languageCodeLiveData
    val region:LiveData<String> = settingRepository.regionCodeLiveData
    fun saveLanguage(language: String) = settingRepository.saveLanguageCode(language)
    fun saveRegion(region: String) = settingRepository.saveRegionCode(region)
}
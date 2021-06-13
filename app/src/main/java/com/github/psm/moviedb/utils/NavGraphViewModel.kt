package com.github.psm.moviedb.utils

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NavGraphViewModel @Inject constructor(): ViewModel() {
    val isTopLevel = MutableStateFlow(false)
}
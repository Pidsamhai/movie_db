package com.github.psm.moviedb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.psm.moviedb.db.Resource
import com.github.psm.moviedb.db.model.Movie
import com.github.psm.moviedb.db.model.tv.popular.Tv
import com.github.psm.moviedb.db.model.upcoming.UpComingResponse
import com.github.psm.moviedb.repository.TMDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TMDBRepository
) : ViewModel() {

    private val _upComings: MutableStateFlow<UpComingResponse?> = MutableStateFlow(null)
    val upComings: Flow<UpComingResponse?> = _upComings

    private val _popularMovie: MutableStateFlow<Resource<List<Movie>>?> = MutableStateFlow(null)
    val popularMovie: Flow<Resource<List<Movie>>?> = _popularMovie

    private val _popularTv: MutableStateFlow<Resource<List<Tv>>?> = MutableStateFlow(null)
    val popularTv: Flow<Resource<List<Tv>>?> = _popularTv

    init {
        refresh(true)
    }

    private fun fetchPopularTv(refresh: Boolean) {
        viewModelScope.launch {
            _popularTv.emitAll(repository.getPopularTvFlow(1, refresh))
        }
    }

    private fun fetchPopularMovie(refresh: Boolean) {
        viewModelScope.launch {
            _popularMovie.emitAll(repository.getPopularMovieFlow(1, refresh))
        }
    }

    private fun fetchUpcoming() {
        viewModelScope.launch {
            _upComings.emitAll(repository.getUpcomingMovieFlow())
        }
    }

    fun refresh(refresh: Boolean = false) {
        fetchPopularMovie(refresh)
        fetchPopularTv(refresh)
        fetchUpcoming()
    }
}

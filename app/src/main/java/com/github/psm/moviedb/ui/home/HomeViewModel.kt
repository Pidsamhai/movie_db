package com.github.psm.moviedb.ui.home

import androidx.lifecycle.*
import com.github.psm.moviedb.db.BoxStore
import com.github.psm.moviedb.db.model.Movie
import com.github.psm.moviedb.db.model.Movie_
import com.github.psm.moviedb.db.model.genre.Genre
import com.github.psm.moviedb.db.model.tv.popular.Tv
import com.github.psm.moviedb.db.model.tv.popular.Tv_
import com.github.psm.moviedb.db.model.upcoming.UpComingResponse
import com.github.psm.moviedb.repository.TMDBRepository
import com.github.psm.moviedb.utils.ObjectBoxLiveData
import com.github.psm.moviedb.utils.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TMDBRepository,
    private val boxStore: BoxStore
) : ViewModel() {
    val upComings: Flow<UpComingResponse> = repository.getUpcomingMovieFlow()
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    val popularMovie: LiveData<List<Movie>>
        get() = boxStore.movie
            .query()
            .orderDesc(Movie_.popularity)
            .orderDesc(Movie_.releaseDate)
            .orderDesc(Movie_.voteCount)
            .orderDesc(Movie_.voteAverage)
            .asLiveData()
            .map {
                it.take(20)
            }

    val popularTv: LiveData<List<Tv>>
        get() = boxStore.tv
            .query()
            .orderDesc(Tv_.popularity)
            .orderDesc(Tv_.firstAirDate)
            .orderDesc(Tv_.voteCount)
            .orderDesc(Tv_.voteAverage)
            .asLiveData()
            .map {
                it.take(20)
            }

    val genres: ObjectBoxLiveData<Genre> = boxStore.genre.query().asLiveData()

    fun feedData() = viewModelScope.launch(Dispatchers.IO) {
        repository.getGenreNormal()
        repository.getPopularMovie(1)
        repository.getPopularTv(1)
        withContext(Dispatchers.Main) {
            _isLoading.value = false
        }
    }
}

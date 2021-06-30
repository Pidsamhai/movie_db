package com.github.psm.moviedb.ui.detail

import androidx.lifecycle.*
import com.github.psm.moviedb.db.BoxStore
import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.db.model.detail.MovieDetail_
import com.github.psm.moviedb.db.model.movie.credit.MovieCredit
import com.github.psm.moviedb.db.model.movie.credit.MovieCredit_
import com.github.psm.moviedb.repository.TMDBRepository
import com.github.psm.moviedb.utils.asFirstLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: TMDBRepository,
    private val boxStore: BoxStore
): ViewModel() {
    private val movieId = MutableLiveData<Long?>(null)

    val movieDetail: LiveData<MovieDetail?> = movieId.switchMap { movieId ->
        boxStore.movieDetail.query().equal(MovieDetail_.id, movieId ?: 0).asFirstLiveData()
    }

    val movieCredit: LiveData<MovieCredit?> = movieId.switchMap { movieId ->
        boxStore.movieCredit.query().equal(MovieCredit_.id, movieId ?: 0).asFirstLiveData()
    }

    fun getMovieDetail(movieId: Long) {
        this.movieId.value = movieId
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMovieDetail(movieId)
            repository.getMovieCredit(movieId)
        }
    }
}
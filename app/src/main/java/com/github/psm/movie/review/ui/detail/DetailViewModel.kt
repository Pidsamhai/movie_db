package com.github.psm.movie.review.ui.detail

import androidx.lifecycle.ViewModel
import com.github.psm.movie.review.db.model.detail.MovieDetail
import com.github.psm.movie.review.repository.TMDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    repository: TMDBRepository
): ViewModel() {
    private val movieId = MutableStateFlow<String?>(null)
    val movieDetail: Flow<MovieDetail> = movieId.flatMapLatest {
        repository.getMovieDetail(it ?: return@flatMapLatest flow {  })
    }

    fun getMovieDetail(movieId: String) {
        this.movieId.value = movieId
    }
}
package com.github.psm.movie.review.ui.detail

import androidx.lifecycle.*
import com.github.psm.movie.review.db.ObjectBox
import com.github.psm.movie.review.db.model.detail.MovieDetail
import com.github.psm.movie.review.db.model.detail.MovieDetail_
import com.github.psm.movie.review.repository.TMDBRepository
import com.github.psm.movie.review.utils.asFilerLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: TMDBRepository
): ViewModel() {
    private val movieId = MutableLiveData<Int?>(null)
    private val movieDetailBox:Box<MovieDetail> = ObjectBox.store.boxFor()
    val movieDetail: LiveData<MovieDetail?> = movieId.switchMap { movieID ->
        movieDetailBox.query().equal(MovieDetail_.id, movieID?.toLong() ?: 0).asFilerLiveData {
            it.firstOrNull()
        }
    }

    fun getMovieDetail(movieId: Int) {
        this.movieId.value = movieId
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMovieDetail(movieId)
        }
    }
}
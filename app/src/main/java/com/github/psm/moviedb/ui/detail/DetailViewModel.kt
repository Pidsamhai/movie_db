package com.github.psm.moviedb.ui.detail

import androidx.lifecycle.*
import com.github.psm.moviedb.db.ObjectBox
import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.db.model.detail.MovieDetail_
import com.github.psm.moviedb.repository.TMDBRepository
import com.github.psm.moviedb.utils.asFilerLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: TMDBRepository,
): ViewModel() {
    private val movieId = MutableLiveData<Long?>(null)
    private val movieDetailBox:Box<MovieDetail> = ObjectBox.store.boxFor()
    val movieDetail: LiveData<MovieDetail?> = movieId.switchMap { movieID ->
        movieDetailBox.query().equal(MovieDetail_.id, movieID ?: 0).asFilerLiveData {
            it.firstOrNull()
        }
    }

    fun getMovieDetail(movieId: Long) {
        this.movieId.value = movieId
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMovieDetail(movieId)
        }
    }
}
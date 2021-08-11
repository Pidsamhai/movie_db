package com.github.psm.moviedb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.psm.moviedb.db.Resource
import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.db.model.movie.credit.MovieCredit
import com.github.psm.moviedb.repository.BookmarkRepository
import com.github.psm.moviedb.repository.TMDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: TMDBRepository,
    bookmarkRepository: BookmarkRepository
) : ViewModel() {
    private val id: Long = requireNotNull(savedStateHandle["id"])
    val isBooked: LiveData<Boolean> = bookmarkRepository.bookState(id)
    val detail: Flow<Resource<MovieDetail>> = repository.getMovieDetail(id)
    val movieCredit: Flow<Resource<MovieCredit>> = repository.getMovieCredit(id)

}
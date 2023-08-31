package com.github.psm.moviedb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.psm.moviedb.db.Resource
import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.db.model.movie.credit.MovieCredit
import com.github.psm.moviedb.repository.BookmarkRepository
import com.github.psm.moviedb.repository.TMDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.internal.ChannelFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailVm @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TMDBRepository,
    bookmarkRepository: BookmarkRepository
) : ViewModel() {
    private val id: Long = requireNotNull(savedStateHandle["id"])
    val isBooked: LiveData<Boolean> = bookmarkRepository.bookState(id, true)
    private val _detail: MutableStateFlow<Resource<MovieDetail>> = MutableStateFlow(Resource.Initial)
    val detail: Flow<Resource<MovieDetail>> = _detail
    private val _movieCredit: MutableStateFlow<Resource<MovieCredit>> = MutableStateFlow(Resource.Initial)
    val movieCredit: Flow<Resource<MovieCredit>?> = _movieCredit

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _detail.emitAll(repository.getMovieDetail(id))
        }

        viewModelScope.launch {
            _movieCredit.emitAll(repository.getMovieCredit(
                id
            ))
        }
    }

}
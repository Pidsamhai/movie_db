package com.github.psm.moviedb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.psm.moviedb.db.Resource
import com.github.psm.moviedb.db.model.tv.credits.TvCredit
import com.github.psm.moviedb.db.model.tv.detail.TvDetail
import com.github.psm.moviedb.repository.BookmarkRepository
import com.github.psm.moviedb.repository.TMDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TvDetailVm @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: TMDBRepository,
    bookmarkRepository: BookmarkRepository
): ViewModel() {
    private val id: Long = requireNotNull(savedStateHandle["id"])
    val isBooked: LiveData<Boolean> = bookmarkRepository.bookState(id, false)
    private val tvId: Long = requireNotNull(savedStateHandle["id"])
    val detail: Flow<Resource<TvDetail>> = repository.getTvDetail(tvId)
    val credit: Flow<Resource<TvCredit>> = repository.getTvCredit(tvId)
}
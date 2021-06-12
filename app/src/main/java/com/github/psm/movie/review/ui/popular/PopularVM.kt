package com.github.psm.movie.review.ui.popular

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.psm.movie.review.db.model.Movie
import com.github.psm.movie.review.repository.TMDBRepository
import com.github.psm.movie.review.ui.movielist.BaseMovieListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PopularVM @Inject constructor(
    private val repository: TMDBRepository,
) : BaseMovieListViewModel() {
    override val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 1)) {
            PopularDataSource(repository)
        }.flow.cachedIn(viewModelScope)
}
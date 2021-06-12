package com.github.psm.movie.review.ui.movielist

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.github.psm.movie.review.db.model.Movie
import kotlinx.coroutines.flow.Flow

abstract class BaseMovieListViewModel: ViewModel() {
    abstract val movies: Flow<PagingData<Movie>>
}
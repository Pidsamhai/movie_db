package com.github.psm.movie.review.ui.home

import androidx.lifecycle.ViewModel
import com.github.psm.movie.review.db.model.Popular
import com.github.psm.movie.review.db.model.genre.Genre
import com.github.psm.movie.review.repository.TMDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: TMDBRepository): ViewModel() {
    val popular: Flow<Popular> = repository.getPopular()
    val genres: Flow<List<Genre>?> = repository.getGenres().map { it.genres }
}
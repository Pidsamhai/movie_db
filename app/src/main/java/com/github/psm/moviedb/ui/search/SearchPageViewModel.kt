package com.github.psm.moviedb.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.psm.moviedb.db.model.Movie
import com.github.psm.moviedb.db.model.params.SearchParams
import com.github.psm.moviedb.db.model.tv.popular.Tv
import com.github.psm.moviedb.repository.TMDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchPageViewModel @Inject constructor(
   private val savedStateHandle: SavedStateHandle,
   private val repository: TMDBRepository
): ViewModel() {
   private val searchParams = MutableStateFlow<SearchParams?>( null)

   val searchMovieResult: Flow<PagingData<Movie>> = searchParams.flatMapLatest { params ->
      if (params == null) flowOf()
      else Pager(PagingConfig(pageSize = 1)) {
         pagingDataSource(
            fetch = { repository.searchMovie(params.keyword, it) },
         )
      }.flow.cachedIn(viewModelScope)
   }

   val searchTvResult: Flow<PagingData<Tv>> = searchParams.flatMapLatest { params ->
      if (params == null) flowOf()
      else Pager(PagingConfig(pageSize = 1)) {
         pagingDataSource(
            fetch = { repository.searchTv(params.keyword, it) },
         )
      }.flow.cachedIn(viewModelScope)
   }

   fun search(params: SearchParams) = viewModelScope.launch(Dispatchers.IO) {
      searchParams.value = params
   }
}
package com.github.psm.moviedb.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.psm.moviedb.db.model.BaseResponse
import com.github.psm.moviedb.db.model.params.SearchParams
import com.github.psm.moviedb.repository.TMDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchPageViewModel @Inject constructor(
   private val savedStateHandle: SavedStateHandle,
   private val repository: TMDBRepository
): ViewModel() {
   private val searchParams = MutableStateFlow<SearchParams?>( null)

   val searchResult: Flow<BaseResponse?> = searchParams.flatMapLatest { params ->
      if (params?.keyword?.isEmpty() == true || params == null) flowOf(BaseResponse())
      else repository.search(params.keyword, params.page)
   }

   fun search(params: SearchParams) = viewModelScope.launch(Dispatchers.IO) {
      searchParams.value = params
   }
}
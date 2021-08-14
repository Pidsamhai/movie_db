package com.github.psm.moviedb.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.psm.moviedb.db.model.Movie
import com.github.psm.moviedb.db.model.params.SearchParams
import com.github.psm.moviedb.db.model.tv.popular.Tv
import com.github.psm.moviedb.ui.widget.BaseAppBar
import com.github.psm.moviedb.ui.widget.SearchTextField

@Composable
fun SearchPage(
    viewModel: SearchPageViewModel = hiltViewModel(),
    onBackPress: () -> Unit = { },
    selectedMovie: (id: Long) -> Unit = { },
    selectedTv: (id: Long) -> Unit = { }
) {
    val searchMovieResult = viewModel.searchMovieResult.collectAsLazyPagingItems()
    val searchTvResult = viewModel.searchTvResult.collectAsLazyPagingItems()
    var searchKeyword by rememberSaveable { mutableStateOf("") }

    SearchPageContent(
        searchMovieResult = searchMovieResult,
        searchTvResult = searchTvResult,
        onBackPress = onBackPress,
        selectedMovie = selectedMovie,
        selectedTv = selectedTv,
        onSearchChange = {
            viewModel.search(SearchParams(it))
            searchKeyword = it
        },
        searchKeyWord = searchKeyword,
    )
}

@Composable
fun SearchPageContent(
    searchMovieResult: LazyPagingItems<Movie>?,
    searchTvResult: LazyPagingItems<Tv>?,
    onBackPress: () -> Unit = { },
    selectedMovie: (id: Long) -> Unit = { },
    selectedTv: (id: Long) -> Unit = { },
    onSearchChange: (keyword: String) -> Unit = { },
    searchKeyWord: String = "",
) {
    val focusRequest = remember { FocusRequester() }

    LaunchedEffect(key1 = Unit) {
        focusRequest.requestFocus()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BaseAppBar(
            startContent = {
                IconButton(onClick = { onBackPress() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            },
            centerContent = {
                SearchTextField(
                    value = searchKeyWord,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequest),
                    onValueChange = {
                        onSearchChange(it)
                    },
                    placeholder = "Search"
                )
            },
            endContent = {
                IconButton(onClick = { onSearchChange(searchKeyWord) }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            }
        )

        SearchResultTabs(
            movieItems = searchMovieResult,
            tvItems = searchTvResult,
            modifier = Modifier.weight(1f),
            selectedMovie = selectedMovie,
            selectedTv = selectedTv
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchPagePreview() {
    SearchPageContent(
        searchMovieResult = null,
        searchTvResult = null
    )
}
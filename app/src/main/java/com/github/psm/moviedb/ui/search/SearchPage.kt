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
    onItemClick: (movieId: Long) -> Unit = { }
) {
    val searchMovieResult = viewModel.searchMovieResult.collectAsLazyPagingItems()
    val searchTvResult = viewModel.searchTvResult.collectAsLazyPagingItems()
    SearchPageContent(
        searchMovieResult = searchMovieResult,
        searchTvResult = searchTvResult,
        onBackPress = onBackPress,
        onItemClick = onItemClick,
        onSearchChange = { viewModel.search(SearchParams(it)) }
    )
}

@Composable
fun SearchPageContent(
    searchMovieResult: LazyPagingItems<Movie>?,
    searchTvResult: LazyPagingItems<Tv>?,
    onBackPress: () -> Unit = { },
    onItemClick: (movieId: Long) -> Unit = { },
    onSearchChange: (keyword: String) -> Unit = { }
) {
    val focusRequest = remember { FocusRequester() }
    var searchKeyword by remember { mutableStateOf("") }

    LaunchedEffect(
        key1 = focusRequest,
        block = {
            focusRequest.requestFocus()
        }
    )

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequest),
                    onValueChange = {
                        onSearchChange(it)
                        searchKeyword = it
                    },
                    placeholder = "Search"
                )
            },
            endContent = {
                IconButton(onClick = { onSearchChange(searchKeyword) }) {
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
            onItemClick = onItemClick
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
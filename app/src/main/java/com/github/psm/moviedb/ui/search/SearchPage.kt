package com.github.psm.moviedb.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.psm.moviedb.db.model.params.SearchParams
import com.github.psm.moviedb.ui.widget.BaseAppBar
import com.github.psm.moviedb.ui.widget.NormalTextField
import com.github.psm.moviedb.ui.widget.movie.MovieSearchItem


@Composable
fun SearchPage(
    viewModel: SearchPageViewModel = hiltViewModel(),
    onBackPress: () -> Unit = { },
    onItemClick: (movieId: Int) -> Unit = { }
) {

    var searchValue by rememberSaveable { mutableStateOf("") }
    val focusRequest = remember { FocusRequester() }
    val searchResult by viewModel.searchResult.collectAsState(initial = null)
    val listState = rememberLazyListState()

    viewModel.search(SearchParams(searchValue))

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
                IconButton(onClick = { onBackPress.invoke() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            },
            centerContent = {
                NormalTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequest),
                    value = searchValue,
                    onValueChange = { text ->  searchValue = text },
                    placeholder = "Search"
                )
            },
            endContent = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        )

        LazyColumn(
            Modifier
                .padding(
                    start = 8.dp,
                    end = 8.dp
                ),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(searchResult?.movies ?: return@LazyColumn) {
                MovieSearchItem(movie = it) { movieId ->
                    onItemClick.invoke(movieId)
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun SearchPagePreview() {
    SearchPage()
}
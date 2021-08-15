package com.github.psm.moviedb.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.psm.moviedb.ui.widget.CustomAppBar
import com.github.psm.moviedb.ui.widget.movie.MovieItem
import com.github.psm.moviedb.ui.widget.movie.MovieItemPlaceHolder
import com.github.psm.moviedb.ui.widget.movie.TvItem
import com.github.psm.moviedb.utils.getValue
import com.github.psm.moviedb.utils.isLoadings
import com.github.psm.moviedb.utils.placeholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun Home(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToSearchPage: () -> Unit = { },
    selectMovie: (id: Long) -> Unit,
    selectTv: (id: Long) -> Unit,
    navigateToPopular: () -> Unit = { },
    navigateToUpComing: () -> Unit = { }
) {
    val searchValue = remember { mutableStateOf(TextFieldValue("")) }
    val listSate = rememberLazyListState()
    val mainScrollState = rememberScrollState()
    val popularMovieResource by homeViewModel.popularMovie.collectAsState(initial = null)
    val popularTvResource by homeViewModel.popularTv.collectAsState(initial = null)
    val popularMovie by popularMovieResource
    val popularTv by popularTvResource
    val isLoading = isLoadings(
        popularMovieResource,
        popularTvResource
    )
    val upcomingMovie by homeViewModel.upComings.collectAsState(initial = null)
    val swipeRefreshState = rememberSwipeRefreshState(isLoading)

    Column {
        CustomAppBar(
            scrollState = mainScrollState
        )
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { homeViewModel.refresh(true) }
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .verticalScroll(mainScrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                navigateToSearchPage.invoke()
                            }
                        }
                        .clickable { navigateToSearchPage.invoke() },
                    label = { Text(text = "Search") },
                    placeholder = { Text(text = "Search") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "Search"
                        )
                    },
                    value = searchValue.value,
                    onValueChange = { text -> searchValue.value = text },
                    singleLine = true
                )

                HeaderItem(
                    header = "Popular",
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                    onEndTextClick = navigateToPopular
                )

                LazyRow(
                    Modifier
                        .fillMaxWidth(),
                    state = listSate,
                    contentPadding = PaddingValues(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    placeholder(
                        enable = { popularMovie == null },
                        itemCount = 5
                    ) {
                        MovieItemPlaceHolder()
                    }
                    items(popularMovie ?: listOf()) {
                        MovieItem(movie = it) { movieId ->
                            selectMovie.invoke(movieId)
                        }
                    }
                }

                // UpComing

                HeaderItem(
                    header = "Upcoming",
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                    onEndTextClick = navigateToUpComing
                )

                LazyRow(
                    Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    placeholder(
                        enable = { popularMovie == null },
                        itemCount = 5
                    ) {
                        MovieItemPlaceHolder()
                    }
                    items(upcomingMovie?.results ?: listOf()) {
                        MovieItem(movie = it) { movieId ->
                            selectMovie.invoke(movieId)
                        }
                    }
                }

                HeaderItem(
                    header = "Tv Popular",
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp),
                    onEndTextClick = { }
                )

                LazyRow(
                    Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    placeholder(
                        enable = { popularTv == null },
                        itemCount = 5
                    ) {
                        MovieItemPlaceHolder()
                    }
                    items(popularTv ?: listOf()) {
                        TvItem(tv = it) { id ->
                            selectTv(id)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreView() {
    Home(
        selectMovie = { },
        selectTv = { },
    )
}
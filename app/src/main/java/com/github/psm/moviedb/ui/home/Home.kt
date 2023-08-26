package com.github.psm.moviedb.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import timber.log.Timber

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
    val contentPadding = 16.dp
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
            scrollState = mainScrollState,
            searchClick = { navigateToSearchPage() }
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
                HeaderItem(
                    header = "Popular",
                    modifier = Modifier.padding(horizontal = contentPadding),
                    onEndTextClick = navigateToPopular
                )

                LazyRow(
                    Modifier
                        .fillMaxWidth(),
                    state = listSate,
                    contentPadding = PaddingValues(contentPadding),
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
                    modifier = Modifier.padding(horizontal = contentPadding),
                    onEndTextClick = navigateToUpComing
                )

                LazyRow(
                    Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(contentPadding),
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
                    modifier = Modifier.padding(horizontal = contentPadding),
                    onEndTextClick = { }
                )

                LazyRow(
                    Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(contentPadding),
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
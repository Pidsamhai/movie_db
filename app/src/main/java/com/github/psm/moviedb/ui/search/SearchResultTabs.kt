package com.github.psm.moviedb.ui.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.github.psm.moviedb.db.model.Movie
import com.github.psm.moviedb.db.model.tv.popular.Tv
import com.github.psm.moviedb.ui.person.PersonContentPage
import com.github.psm.moviedb.ui.widget.Loader
import com.github.psm.moviedb.ui.widget.LoaderStyle
import com.github.psm.moviedb.ui.widget.movie.SearchItem
import com.github.psm.moviedb.utils.TabPages
import androidx.compose.foundation.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

private val tabPage = object : TabPages {
    val MovieResult: Int = 0
    val TvResult: Int = 1
    override val pages: List<Int> = listOf(
        MovieResult,
        TvResult
    )
    override val titles: List<String> = listOf(
        "Movie",
        "TV"
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun SearchResultTabs(
    modifier: Modifier = Modifier,
    movieItems: LazyPagingItems<Movie>? = null,
    tvItems: LazyPagingItems<Tv>? = null,
    selectedMovie: (id: Long) -> Unit = { },
    selectedTv: (id: Long) -> Unit = { }
) {
    val pageState = rememberPagerState {
        return@rememberPagerState 2;
    }
    val coroutineScope = rememberCoroutineScope()
    val changePage: (index: Int) -> Unit = {
        coroutineScope.launch {
            pageState.animateScrollToPage(it)
        }
    }

    Column(
        modifier = modifier
    ) {
        TabRow(
            backgroundColor = MaterialTheme.colors.surface,
            selectedTabIndex = PersonContentPage.Biography,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pageState, tabPositions)
                )
            }
        ) {
            tabPage.titles.forEachIndexed { index, title ->
                Tab(
                    selected = pageState.currentPage == index,
                    onClick = { changePage(index) }
                ) {
                    Text(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 12.dp,
                            bottom = 12.dp
                        ),
                        text = title
                    )
                }
            }
        }

        HorizontalPager(state = pageState) { page ->
            when(page) {
                tabPage.MovieResult -> {
                    SearchResult(
                        items = movieItems,
                    ) {
                        SearchItem(movie = it) { id ->
                            selectedMovie(id)
                        }
                    }
                }
                tabPage.TvResult -> {
                    SearchResult(
                        items = tvItems,
                    ) {
                        SearchItem(tv = it) { id ->
                            selectedTv(id)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun <T: Any>SearchResult(
    items: LazyPagingItems<T>?,
    item: @Composable (item: T) -> Unit
) {
    val ifRetry = items?.loadState?.source?.refresh is LoadState.Loading
    val ifFail = items?.loadState?.source?.refresh is LoadState.Error
    val isLoading = items?.loadState?.append is LoadState.Loading
    val refreshState = rememberSwipeRefreshState(isRefreshing = false)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (ifFail) {
            TextButton(
                modifier = Modifier.padding(16.dp),
                onClick = { items?.retry() }
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                Text(text = "Retry")
            }
        }
        SwipeRefresh(
            modifier = Modifier
                .weight(1f),
            state = refreshState,
            onRefresh = { items?.refresh() }
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(items?.itemCount ?: return@LazyColumn) { index ->
                    item(item = items[index] ?: return@items)
                }
            }
        }
        if (isLoading || ifRetry) {
            Loader(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                height = 48.dp,
                width = Dp.Infinity,
                loaderStyle = LoaderStyle.Dot
            )
        }
    }
}
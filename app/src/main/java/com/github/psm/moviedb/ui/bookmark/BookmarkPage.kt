package com.github.psm.moviedb.ui.bookmark

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.psm.moviedb.NavigationRoutes
import com.github.psm.moviedb.db.model.BookMarkType
import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.db.model.tv.detail.TvDetail
import com.github.psm.moviedb.ui.widget.BaseAppBar
import com.github.psm.moviedb.ui.widget.SwipeToDismissItem
import com.github.psm.moviedb.ui.widget.movie.BookmarkItem
import kotlinx.coroutines.launch

@Composable
fun BookmarkPage(
    viewModel: BookmarkVM = hiltViewModel(),
    navigateToMovieDetail: (id: Long) -> Unit,
    navigateToTvDetail: (id: Long) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val bookmarks by viewModel.bookmarks.observeAsState()
    val scrollState = rememberScrollState()
    val listState = rememberLazyListState()

    val undoUnBookmark: () -> Unit = {
        coroutineScope.launch {
            viewModel.undoUnBookmark()
        }
    }

    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        BookmarkPageContent(
            bookmarks = bookmarks,
            scrollState = scrollState,
            listState = listState,
            navigateToMovieDetail = navigateToMovieDetail,
            navigateToTvDetail = navigateToTvDetail,
            unBookCallback = { id, title, isMovie ->
                viewModel.unBook(id, isMovie)
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        "Removed ${title ?: id}", "Undo"
                    ).also { snackResult ->
                        when (snackResult) {
                            SnackbarResult.Dismissed -> Unit
                            SnackbarResult.ActionPerformed -> undoUnBookmark()
                        }
                    }
                }
            },
        )

        SnackbarHost(
            hostState = scaffoldState.snackbarHostState
        ) { snackbarData ->
            Snackbar(
                snackbarData = snackbarData,
                backgroundColor = MaterialTheme.colors.error,
                actionColor = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookmarkPageContent(
    bookmarks: List<BookMarkType>? = null,
    scrollState: ScrollState = rememberScrollState(),
    listState: LazyListState = rememberLazyListState(),
    unBookCallback: (id: Long, title: String?, isMovie: Boolean) -> Unit = { _, _, _ -> },
    navigateToMovieDetail: (id: Long) -> Unit = { },
    navigateToTvDetail: (id: Long) -> Unit = { }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        BaseAppBar(
            title = NavigationRoutes.BookmarkPage.label,
            listState = listState
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 8.dp, end = 8.dp, top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = listState
        ) {
            items(bookmarks ?: listOf()) {
                when (it) {
                    is BookMarkType.Movie -> {
                        SwipeToDismissItem(
                            item = it.data,
                            unBookCallback = { unBookCallback(it.data.id, it.data.title, true) }
                        ) { item ->
                            BookmarkItem(
                                detail = item,
                                onClick = navigateToMovieDetail
                            )
                        }
                    }
                    is BookMarkType.Tv -> {
                        SwipeToDismissItem(
                            item = it.data,
                            unBookCallback = { unBookCallback(it.data.id, it.data.name, false) }
                        ) { item ->
                            BookmarkItem(
                                detail = item,
                                onClick = navigateToTvDetail
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookmarkPagePreview() {
    val items = listOf(
        BookMarkType.Movie(
            MovieDetail(
                title = "Marvel Avenger",
                voteAverage = 8.9,
                overview = "What’s the secret to rich and tender squid? Always use sweet thyme."
            )
        ),
        BookMarkType.Tv(
            TvDetail(
                name = "Forest",
                voteAverage = 5.0,
                overview = "Try whisking the chickpeas juice chilis with salty kefir and vinaigrette, refrigerated.."
            )
        )
    )
    BookmarkPageContent(
        bookmarks = items
    )
}
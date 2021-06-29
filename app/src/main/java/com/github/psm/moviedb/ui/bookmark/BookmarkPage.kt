package com.github.psm.moviedb.ui.bookmark

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.psm.moviedb.NavigationRoutes
import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.ui.widget.BaseAppBar
import com.github.psm.moviedb.ui.widget.movie.MovieBookmarkItem
import kotlinx.coroutines.launch

@Composable
fun BookmarkPage(
    viewModel: BookmarkVM = hiltViewModel(),
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
            unBookCallback = { movieId, title ->
                viewModel.unBook(movieId)
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        "Removed ${ title ?: movieId }", "Undo"
                    ).also {  snackResult ->
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
    bookmarks: List<MovieDetail>? = null,
    scrollState: ScrollState = rememberScrollState(),
    listState: LazyListState = rememberLazyListState(),
    unBookCallback: (movieId: Long, title: String?) -> Unit = {_, _ -> },
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        BaseAppBar(
            title = NavigationRoutes.BookmarkPage.label
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
                var removeItem by remember { mutableStateOf(false) }
                if (removeItem) {
                    it.id.let { movieId ->
                        unBookCallback(movieId, it.title)
                        removeItem = !removeItem
                    }
                }
                val dismissState = rememberDismissState(
                    confirmStateChange = { dismissValue ->
                        if (dismissValue == DismissValue.DismissedToStart) removeItem = !removeItem
                        dismissValue != DismissValue.DismissedToStart
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = {
                        val direction = dismissState.dismissDirection ?: return@SwipeToDismiss

                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> MaterialTheme.colors.error.copy(alpha = 0.7f)
                                DismissValue.DismissedToStart -> MaterialTheme.colors.error
                                DismissValue.DismissedToEnd -> Color.Green
                            }
                        )
                        val alignment = when (direction) {
                            DismissDirection.StartToEnd -> Alignment.CenterStart
                            DismissDirection.EndToStart -> Alignment.CenterEnd
                        }
                        val icon = when (direction) {
                            DismissDirection.StartToEnd -> Icons.Default.Done
                            DismissDirection.EndToStart -> Icons.Default.Delete
                        }
                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                        )

                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                icon,
                                contentDescription = "Localized description",
                                modifier = Modifier.scale(scale)
                            )
                        }
                    }
                ) {
                    MovieBookmarkItem(movieDetail = it)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookmarkPagePreview() {
    val items = listOf(
        MovieDetail(
            title = "Marvel Avenger",
            voteAverage = 8.9,
            overview = "What’s the secret to rich and tender squid? Always use sweet thyme."
        )
    )
    BookmarkPageContent(
        bookmarks = items
    )
}
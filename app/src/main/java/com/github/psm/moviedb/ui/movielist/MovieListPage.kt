package com.github.psm.moviedb.ui.movielist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.psm.moviedb.ui.widget.BaseAppBar
import com.github.psm.moviedb.ui.widget.Loader
import com.github.psm.moviedb.ui.widget.LoaderStyle
import com.github.psm.moviedb.ui.widget.movie.MovieItem
import com.github.psm.moviedb.utils.items

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieListPage(
    title: String,
    onBackClick: () -> Unit = { },
    selectedMovie: (movieId: Long) -> Unit = {},
    viewModel: BaseMovieListViewModel,
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()
    val ifRetry = movies.loadState.source.refresh is LoadState.Loading
    val ifFail = movies.loadState.source.refresh is LoadState.Error
    val listState = rememberLazyListState()

    Column {
        BaseAppBar(
            startContent = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            centerContent = {
                Text(text = title, fontWeight = FontWeight.Bold)
            },
            listState = listState
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(2),
                    state = listState
                ) {
                    items(movies) { movie ->
                        MovieItem(
                            modifier = Modifier.padding(8.dp),
                            movie = movie ?: return@items,
                            onClick = selectedMovie
                        )
                    }
                }
                if (ifFail) {
                    Text(
                        modifier = Modifier
                            .clickable {
                                movies.retry()
                            },
                        text = "Can't load data",
                    )
                }

                if (ifRetry) Loader(size = 30.dp)

            }
            if (movies.loadState.append is LoadState.Error) {
                Snackbar(
                    backgroundColor = MaterialTheme.colors.error,
                    action = {
                        OutlinedButton(
                            onClick = { movies.retry() }
                        ) {
                            Text(text = "Retry")
                        }
                    }
                ) {
                    Text(text = "Error")
                }
            }
        }

        if (movies.loadState.append is LoadState.Loading) {
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
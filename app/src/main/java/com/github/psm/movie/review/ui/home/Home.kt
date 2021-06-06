package com.github.psm.movie.review.ui.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.psm.movie.review.ui.category.CategoryItem
import com.github.psm.movie.review.ui.widget.CustomAppBar
import com.github.psm.movie.review.ui.widget.Loader
import com.github.psm.movie.review.ui.widget.movie.MovieItem
import timber.log.Timber

@Composable
fun Home(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(),
    navigateToSearchPage: () -> Unit = { },
    selectMovie: (movieId: Int) -> Unit
) {
    val searchValue = remember { mutableStateOf(TextFieldValue("")) }
    val listSate = rememberLazyListState()
    val mainScrollState = rememberScrollState()
    val genresScrollState = rememberScrollState()
    val populars by homeViewModel.movieResponse.observeAsState()
    val context = LocalContext.current
    val genres by homeViewModel.genres.observeAsState()

    Timber.i("Genres ${genres?.size}")

    Column {
        CustomAppBar(
            scrollState = mainScrollState
        )

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
                        when (focusState) {
                            FocusState.Active -> {
                                Timber.i("Active")
                                navigateToSearchPage.invoke()
                            }
                            FocusState.ActiveParent -> {
                                Timber.i("ActiveParent")
                            }
                            FocusState.Captured -> {
                                Timber.i("Captured")
                            }
                            FocusState.Disabled -> {
                                Timber.i("Disabled")
                            }
                            FocusState.Inactive -> {
                                Timber.i("Inactive")
                            }
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

            // Category

            HeaderItem(
                header = "Categories",
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp),
                onEndTextClick = {
                    Toast.makeText(context, "See more", Toast.LENGTH_SHORT).show()
                }
            )

            // Categories Items

            if (genres?.size ?: 0 == 0)
                Loader(
                    Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(
                            start = 24.dp,
                            end = 24.dp,
                            top = 16.dp,
                            bottom = 16.dp
                        ),
                    size = 50.dp
                )
            else
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 24.dp,
                            end = 24.dp,
                            top = 16.dp,
                            bottom = 16.dp
                        )
                        .horizontalScroll(state = genresScrollState),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    repeat(genres?.take(5)?.size ?: 0) {
                        CategoryItem(text = genres?.get(it)?.name!!)
                    }

                }

            HeaderItem(header = "Popular", modifier = Modifier.padding(start = 24.dp, end = 24.dp))

            if (populars?.size ?: 0 == 0)
                Loader(
                    Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .padding(
                            start = 24.dp,
                            end = 24.dp,
                            top = 16.dp,
                            bottom = 16.dp
                        ),
                    size = 50.dp
                )
            else
                LazyRow(
                    Modifier
                        .fillMaxWidth(),
                    state = listSate,
                    contentPadding = PaddingValues(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(populars?.size ?: 0) {
                        MovieItem(movie = populars?.get(it)!!) { movieId ->
                            selectMovie.invoke(movieId)
                        }
                    }
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreView() {
    Home(selectMovie = { })
}
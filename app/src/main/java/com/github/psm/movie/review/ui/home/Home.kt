package com.github.psm.movie.review.ui.home

import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.psm.movie.review.ui.category.CategoryItem
import com.github.psm.movie.review.ui.movie.MovieItem

@Composable
fun Home(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(),
    selectMovie: (movieId: String) -> Unit
) {
    val searchValue = remember { mutableStateOf(TextFieldValue("")) }
    val listSate = rememberLazyListState()
    val scrollState = rememberLazyListState()
    val categoryScrollState = rememberScrollState()
    val popular = homeViewModel.popular.collectAsState(initial = null)
    val context = LocalContext.current

    LazyColumn(
        state = scrollState,
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                label = { Text(text = "Search") },
                keyboardActions = KeyboardActions(
                    onSearch = {
                        print("Search")
                    }
                ),
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
        }
        // Category

        item {
            HeaderItem(
                header = "Categories",
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp),
                onEndTextClick = {
                    Toast.makeText(context, "See more", Toast.LENGTH_SHORT).show()
                }
            )
        }

        // Categories Items

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 16.dp,
                        bottom = 16.dp
                    )
                    .horizontalScroll(state = categoryScrollState),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                repeat(4) {
                    CategoryItem(text = "Horror $it")
                }

            }
        }

        item {
            HeaderItem(header = "Popular", modifier = Modifier.padding(start = 24.dp, end = 24.dp))
        }


        item {
            LazyRow(
                Modifier
                    .fillMaxWidth(),
                state = listSate,
                contentPadding = PaddingValues(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(popular.value?.results?.size ?: 0) {
                    MovieItem(result = popular.value?.results?.get(it)!!)
                }
            }
        }

//        item {
//            LazyRow(
//                Modifier
//                    .fillMaxWidth(),
//                contentPadding = PaddingValues(24.dp),
//                horizontalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                items(10) {
//                    MovieItem(
//                        url = "https://image.tmdb.org/t/p/original/z8CExJekGrEThbpMXAmCFvvgoJR.jpg",
//                        description = "Descriptions $it"
//                    )
//                }
//            }
//        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomePreView() {
    Home(selectMovie = { })
}
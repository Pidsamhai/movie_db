package com.github.psm.moviedb.ui.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.psm.moviedb.ui.bookmark.BookmarkVM

@Composable
fun MovieDetailPage(
    movieId: Long,
    navigateBack: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel(),
    bookmarkViewModel: BookmarkVM = hiltViewModel(),
    navigateToPerson: (personId: Long) -> Unit
) {
    val detail by detailViewModel.movieDetail.observeAsState()
    val bookmarkState by detailViewModel.isBooked.observeAsState(false)
    val credit by detailViewModel.movieCredit.observeAsState()

    LaunchedEffect(key1 = movieId) {
        detailViewModel.getMovieDetail(movieId)
    }

    DetailContent(
        appBarTitle = "Movie",
        title = detail?.title,
        posterPath = detail?.posterPath,
        backDropPath = detail?.backdropPath,
        voteAverage = detail?.voteAverage,
        runtime = detail?.runtime,
        genres = detail?.genres,
        overview = detail?.overview,
        credit = credit,
        isBooked = bookmarkState,
        onBookMarkClick = { booked ->
            when {
                booked -> bookmarkViewModel.booking(movieId, true)
                else -> bookmarkViewModel.unBook(movieId)
            }
        },
        navigateToPerson = navigateToPerson,
        navigateBack = navigateBack
    )
}
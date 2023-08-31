package com.github.psm.moviedb.ui.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.psm.moviedb.db.Resource
import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.db.model.movie.credit.MovieCredit
import com.github.psm.moviedb.ui.bookmark.BookmarkVM
import com.github.psm.moviedb.ui.widget.ErrorContent
import timber.log.Timber

@Composable
fun MovieDetailPage(
    navigateBack: () -> Unit,
    movieDetailVm: MovieDetailVm = hiltViewModel(),
    bookmarkViewModel: BookmarkVM = hiltViewModel(),
    navigateToPerson: (personId: Long) -> Unit
) {
    val isBooked by movieDetailVm.isBooked.observeAsState(false)
    val detailResource by movieDetailVm.detail.collectAsState(initial = Resource.Loading)
    val creditResource by movieDetailVm.movieCredit.collectAsState(initial = Resource.Loading)
    var detail: MovieDetail? = null
    var credit: MovieCredit? = null

    when (detailResource) {
        is Resource.Success -> {
            detail = (detailResource as Resource.Success<MovieDetail>).data
            Timber.d("Load State Success %s" , detail.id )
        }
        is Resource.Error -> {
            Timber.d("Load State Error %s", (detailResource as Resource.Error).error)
        }

        else -> {
            Timber.d("Load State Unknown %s", detailResource)
        }
    }

    when (creditResource) {
        is Resource.Success -> {
            credit = (creditResource as Resource.Success<MovieCredit>).data
        }
        is Resource.Error -> {
        }

        else -> {}
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
        isBooked = isBooked,
        onBookMarkClick = { booked ->
            val id = detail?.id ?: return@DetailContent
            when {
                booked -> bookmarkViewModel.booking(id, true)
                else -> bookmarkViewModel.unBook(id, true)
            }
        },
        navigateToPerson = navigateToPerson,
        navigateBack = navigateBack,
        state = detailResource,
        onRetry = {
            movieDetailVm.refresh()
        }
    )
}
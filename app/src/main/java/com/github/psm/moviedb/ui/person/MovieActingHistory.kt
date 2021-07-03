package com.github.psm.moviedb.ui.person

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.psm.moviedb.db.model.person.movie.PersonMovieCredit
import com.github.psm.moviedb.ui.widget.ActingHistoryItem

@Composable
fun MovieActingHistory(
    personMovieCredit: PersonMovieCredit?,
    onMovieClick: (movieId: Long) -> Unit = { }
) {
    val sortCasts = personMovieCredit?.movieCast?.sortedByDescending { it.releaseDate }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        sortCasts?.let { cast ->
            items(cast) {
                ActingHistoryItem(
                    movieCast = it,
                    onClick = onMovieClick
                )
            }
        }
    }
}
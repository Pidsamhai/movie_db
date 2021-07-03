package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.moviedb.db.model.person.movie.MovieCast
import com.github.psm.moviedb.db.model.person.tv.TvCast

@Composable
fun ActingHistoryItem(
    tvCast: TvCast,
    onClick: (tvId: Long) -> Unit = { }
) = ActingHistoryContent(
    year = tvCast.firstAirDate?.split("-")?.get(0),
    name = tvCast.name,
    character = tvCast.character,
    onClick = onClick,
    id = tvCast.id
)

@Composable
fun ActingHistoryItem(
    movieCast: MovieCast,
    onClick: (movieId: Long) -> Unit = { }
) = ActingHistoryContent(
    year = movieCast.releaseDate?.split("-")?.get(0),
    name = movieCast.title,
    character = movieCast.character,
    onClick = onClick,
    id = movieCast.id
)

@Composable
private fun ActingHistoryContent(
    year: String?,
    name: String?,
    character: String?,
    id: Long,
    onClick: (id: Long) -> Unit
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .horizontalScroll(scrollState)
            .background(
                MaterialTheme.colors.surface
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.width(50.dp),
            text = if (year.isNullOrEmpty()) "-" else year
        )

        Column {
            Text(
                modifier = Modifier.clickable {
                    onClick(id)
                },
                text = name ?: "",
                fontWeight = FontWeight.Bold
            )
            Text(text = "as ${character ?: ""}")
        }
    }
}

@Preview(showBackground = true, device = Devices.DEFAULT)
@Composable
private fun ActingHistoryContentPreview() {
    ActingHistoryContent(
        year = "1999-9-9",
        name = "Name",
        character = "Character",
        id = 10,
        onClick = { }
    )
}
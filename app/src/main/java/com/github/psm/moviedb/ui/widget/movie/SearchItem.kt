package com.github.psm.moviedb.ui.widget.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.moviedb.db.model.Movie
import com.github.psm.moviedb.db.model.tv.popular.Tv
import com.github.psm.moviedb.ui.theme.TripleE
import com.github.psm.moviedb.ui.widget.Image
import com.github.psm.moviedb.utils.toImgUrl


@Composable
fun SearchItem(
    movie: Movie,
    onClick: (movieId: Long) -> Unit = { }
) {
    SearchItemContent(
        posterPath = movie.posterPath,
        title = movie.title,
        releaseDate = movie.releaseDate,
        overview = movie.overview,
        onClick = { onClick(movie.id) }
    )
}

@Composable
fun SearchItem(
    tv: Tv,
    onClick: (movieId: Long) -> Unit = { }
) {
    SearchItemContent(
        posterPath = tv.posterPath,
        title = tv.name,
        releaseDate = tv.firstAirDate,
        overview = tv.overview,
        onClick = { onClick(tv.id) }
    )
}

@Composable
private fun SearchItemContent(
    onClick: () -> Unit = { },
    posterPath: String? = null,
    title: String? = null,
    releaseDate: String? = null,
    overview: String? = null
) {
    Card(
        modifier = Modifier
            .background(TripleE)
            .height(150.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick()
                }
        ) {
            Image(
                modifier = Modifier
                    .height(150.dp)
                    .width(100.dp),
                data = posterPath?.toImgUrl(),
                enablePlaceHolder = true,
                fadeIn = true
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = title ?: "",
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = releaseDate ?: "",
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = overview ?: "")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieSearchItemPreView() {
    SearchItem(
        movie = Movie(
            title = "Title",
            releaseDate = "May 1999",
            overview = "Everything we do is connected with pain: moonlight, mineral, control, dimension."
        )
    )
}



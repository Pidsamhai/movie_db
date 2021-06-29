package com.github.psm.moviedb.ui.widget.movie

import androidx.compose.foundation.Image
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
import com.github.psm.moviedb.ui.theme.MovieGrey
import com.github.psm.moviedb.ui.theme.TripleE
import com.github.psm.moviedb.utils.toImgUrl
import com.google.accompanist.coil.rememberCoilPainter


@Composable
fun MovieSearchItem(
    movie: Movie,
    onClick: (movieId: Long) -> Unit = { }
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
                    onClick.invoke(movie.id)
                }
        ) {
            Image(
                modifier = Modifier
                    .height(150.dp)
                    .width(100.dp)
                    .background(MovieGrey),
                painter = rememberCoilPainter(request = movie.posterPath?.toImgUrl()),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = movie.title ?: "",
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = movie.releaseDate ?: "",
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = movie.overview ?: "")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieSearchItemPreView() {
    MovieSearchItem(
        movie = Movie(
            title = "Title",
            releaseDate = "May 1999",
            overview = "Everything we do is connected with pain: moonlight, mineral, control, dimension."
        )
    )
}



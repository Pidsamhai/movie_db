package com.github.psm.moviedb.ui.widget.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.ui.widget.DefaultError
import com.github.psm.moviedb.ui.widget.DefaultLoader
import com.github.psm.moviedb.ui.widget.Image
import com.github.psm.moviedb.utils.toImgUrl

@Composable
fun MovieBookmarkItem(
    movieDetail: MovieDetail
) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable {

                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(60.dp),
                request = movieDetail.posterPath?.toImgUrl(),
                loading = { DefaultLoader() },
                error = { DefaultError() }
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(text = movieDetail.title ?: "")
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = movieDetail.overview ?: "",
                    maxLines = 3
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MovieBookmarkItemPreview() {
    MovieBookmarkItem(
        MovieDetail(
            title = "Command me scallywag, ye wet anchor!"
        )
    )
}
package com.github.psm.moviedb.ui.widget.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.psm.moviedb.db.model.VoteStar
import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.db.model.tv.detail.TvDetail
import com.github.psm.moviedb.ui.widget.Image
import com.github.psm.moviedb.ui.widget.Rating
import com.github.psm.moviedb.utils.toImgUrl

@Composable
fun BookmarkItem(
    detail: MovieDetail,
    onClick: (id: Long) -> Unit = { }
) {
    BookMarkItemContent(
        title = detail.title,
        posterPath = detail.posterPath,
        voteStar = detail.voteStar,
        voteAvg = detail.voteAverage,
        overview = detail.overview,
        onClick = { onClick(detail.id) }
    )
}

@Composable
fun BookmarkItem(
    detail: TvDetail,
    onClick: (id: Long) -> Unit = { }
) {
    BookMarkItemContent(
        title = detail.name,
        posterPath = detail.posterPath,
        voteStar = detail.voteStar,
        voteAvg = detail.voteAverage,
        overview = detail.overview,
        onClick = { onClick(detail.id) }
    )
}

@Composable
private fun BookMarkItemContent(
    title: String?,
    posterPath: String?,
    voteStar: VoteStar,
    voteAvg: Double?,
    overview: String?,
    onClick: () -> Unit = { }
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
                    onClick()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(60.dp),
                data = posterPath?.toImgUrl(),
                contentScale = ContentScale.FillBounds,
                enablePlaceHolder = true
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = title ?: "",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.size(8.dp))
                Row {
                    Rating(voteStar = voteStar)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = voteAvg?.toString() ?: "",
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = overview ?: "",
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MovieBookmarkItemPreview() {
    BookmarkItem(
        MovieDetail(
            voteAverage = 8.9,
            title = "Command me scallywag, ye wet anchor!",
            overview = "Nunquam desiderium ignigena.Trabem sed mire ducunt ad rusticus axona." +
                    "Mash up the avocado with ripe butter, jasmine, oregano, and szechuan pepper making sure to cover all of it." +
                    "Try shredding tuna salad rubed with vinaigrette."
        )
    )
}
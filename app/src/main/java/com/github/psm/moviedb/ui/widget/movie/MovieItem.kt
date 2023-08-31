package com.github.psm.moviedb.ui.widget.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.Placeholder
import com.github.psm.moviedb.db.model.Movie
import com.github.psm.moviedb.db.model.VoteStar
import com.github.psm.moviedb.db.model.tv.popular.Tv
import com.github.psm.moviedb.ui.widget.Gauge
import com.github.psm.moviedb.ui.widget.Image
import com.github.psm.moviedb.ui.widget.Rating
import com.github.psm.moviedb.utils.toImgUrl
import com.valentinilk.shimmer.shimmer

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: ((id: Long) -> Unit)? = null,
) {
    MovieItemContent(
        modifier = modifier,
        posterPath = movie.posterPath,
        voteAvg = movie.voteAverage,
        releaseDate = movie.releaseDate,
        title = movie.title,
        voteStar = movie.voteStar,
        onClick = { onClick?.invoke(movie.id) }
    )
}

@Composable
fun TvItem(
    modifier: Modifier = Modifier,
    tv: Tv,
    onClick: ((id: Long) -> Unit)? = null,
) {
    MovieItemContent(
        modifier = modifier,
        posterPath = tv.posterPath,
        voteAvg = tv.voteAverage,
        releaseDate = tv.firstAirDate,
        title = tv.name,
        voteStar = tv.voteStar,
        onClick = { onClick?.invoke(tv.id) }
    )
}

@Composable
fun MovieItemContent(
    modifier: Modifier,
    posterPath: String?,
    voteAvg: Double?,
    releaseDate: String?,
    title: String?,
    voteStar: VoteStar,
    onClick: () -> Unit = { }
) {
    Card(
        modifier = modifier
            .width(150.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
        ) {
            /**
             * Image With Gauge
             */
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {

                val (imgRef, gaugeRef) = createRefs()

                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(imgRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    data = posterPath?.toImgUrl(),
                    contentScale = ContentScale.FillBounds,
                    fadeIn = true,
                    fadeInDurationMs = 2000,
                    enablePlaceHolder = true
                )

                Row(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = 50.dp,
                                bottomStart = 50.dp
                            )
                        )
                        .background(
                            Color.Black.copy(alpha = 0.6f)
                        )
                        .constrainAs(gaugeRef) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Gauge(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        value = ((voteAvg ?: 0.0) * 0.1).toFloat(),
                        bgColor = Color.Black,
                        strokeSize = 8f,
                        fontSize = 12.sp
                    )
                    Column(
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp
                        )
                    ) {
                        Text(
                            text = "Release Date",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                        Text(
                            text = releaseDate ?: "",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

            /**
             * Title
             */

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp),
                text = title ?: "",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.size(8.dp))

            /**
             * Star
             */
            Rating(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    ),
                voteStar = voteStar
            )
        }
    }
}

@Preview(name = "Placeholder")
@Composable
fun MovieItemPlaceHolder() {
    MovieItem(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Gray.copy(alpha = 0.8f))
            .shimmer(),
        movie = Movie()
    )
}

@Preview(name = "MovieItem")
@Composable
private fun MovieItemPreview() {
    MovieItem(
        movie = Movie(
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            voteAverage = 5.0
        )
    )
}
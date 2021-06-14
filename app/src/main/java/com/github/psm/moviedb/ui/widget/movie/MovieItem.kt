package com.github.psm.moviedb.ui.widget.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.psm.moviedb.R
import com.github.psm.moviedb.db.model.Movie
import com.github.psm.moviedb.ui.widget.Guard
import com.github.psm.moviedb.ui.widget.Image
import com.github.psm.moviedb.ui.widget.Loader
import com.github.psm.moviedb.ui.widget.Rating
import com.github.psm.moviedb.utils.toImgUrl

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: ((movieId: Long) -> Unit)? = null
) {
    Card(
        modifier = modifier,
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .width(150.dp)
                .clickable {
                    onClick?.invoke((movie.id ?: return@clickable))
                }
        ) {
            /**
             * Image With Gauge
             */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        request = movie.posterPath?.toImgUrl(),
                        contentScale = ContentScale.FillBounds,
                        fadeIn = true,
                        fadeInDurationMs = 2000,
                        error = {
                            Icon(
                                modifier = Modifier.size(50.dp),
                                painter = painterResource(id = R.drawable.ic_round_broken_image),
                                contentDescription = null,
                                tint = LocalContentColor.current.copy(alpha = 0.5f)
                            )
                        },
                        loading = {
                            Loader(
                                size = 30.dp
                            )
                        }
                    )
                }
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.BottomEnd
                ) {
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
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Guard(
                            Modifier
                                .size(50.dp)
                                .clip(CircleShape),
                            value = ((movie.voteAverage ?: 0.0) * 0.1).toFloat(),
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
                                fontSize = 12.sp
                            )
                            Text(
                                text = movie.releaseDate ?: "",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
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
                text = movie.title ?: "",
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
                voteStar = movie.voteStar
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    MovieItem(
        movie = Movie(
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            voteAverage = 5.0
        )
    )
}
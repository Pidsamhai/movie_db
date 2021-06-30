package com.github.psm.moviedb.ui.detail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.psm.moviedb.R
import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.db.model.movie.credit.Cast
import com.github.psm.moviedb.db.model.movie.credit.MovieCredit
import com.github.psm.moviedb.ui.bookmark.BookmarkVM
import com.github.psm.moviedb.ui.home.HeaderItem
import com.github.psm.moviedb.ui.theme.Stared
import com.github.psm.moviedb.ui.widget.*
import com.github.psm.moviedb.ui.widget.movie.CastingItem
import com.github.psm.moviedb.utils.toImgUrl

@Composable
fun Detail(
    movieId: Long,
    navigateBack: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel(),
    bookmarkViewModel: BookmarkVM = hiltViewModel()
) {
    val movieDetail by detailViewModel.movieDetail.observeAsState()
    val bookmarkState by bookmarkViewModel.bookState(movieId).observeAsState(false)
    val movieCredit by detailViewModel.movieCredit.observeAsState()

    LaunchedEffect(key1 = movieId) {
        detailViewModel.getMovieDetail(movieId)
    }

    DetailContent(
        movieDetail = movieDetail,
        isBooked = bookmarkState,
        navigateBack = navigateBack,
        movieCredit = movieCredit,
        onBookMarkClick = { booked ->
            when {
                booked -> bookmarkViewModel.booking(movieId)
                else -> bookmarkViewModel.unBook(movieId)
            }
        }
    )
}

@Composable
fun DetailContent(
    movieDetail: MovieDetail?,
    movieCredit: MovieCredit?,
    isBooked: Boolean,
    navigateBack: () -> Unit = { },
    onBookMarkClick: (booked: Boolean) -> Unit = { }
) {
    var expandedOverView by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val genreScrollState = rememberScrollState()
//    var color by remember { mutableStateOf(Color.White) }
//    val colorAnim = animateColorAsState(targetValue = color)

    Column(
        modifier = Modifier
            .fillMaxSize(),
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        colorAnim.value,
//                        MaterialTheme.colors.background,
//                        MaterialTheme.colors.background
//                    ),
//                )
//            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DetailAppBar(
            onBackClick = navigateBack,
            scrollState = scrollState,
            onBookMarkClick = onBookMarkClick,
            bookmarkState = isBooked
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .padding(top = 8.dp),
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp)
            ) {
                Image(
                    modifier = Modifier
                        .width(250.dp)
                        .aspectRatio(2f / 3),
                    request = movieDetail?.posterPath?.toImgUrl(),
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

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Gauge(
                    Modifier
                        .size(60.dp)
                        .padding(4.dp)
                        .clip(CircleShape),
                    value = ((movieDetail?.voteAverage ?: 0.0) * 0.1).toFloat(),
                    bgColor = Color.Black,
                    strokeSize = 8f,
                    fontSize = 12.sp,
                    animated = true
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = movieDetail?.title ?: "",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${movieDetail?.voteAverage ?: ""}",
                            modifier = Modifier.alpha(0.8f),
                            color = MaterialTheme.colors.onSurface,
                            style = MaterialTheme.typography.body2
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Stared
                        )
                    }
                }
            }

            /**
             * Director And Toime
             */

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    horizontalAlignment = Alignment.Start,
                ) {
                    movieCredit?.crew?.find { it.department?.contains("Directing", true) == true }
                        ?.let {
                            Text(
                                text = it.name ?: "",
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colors.onSurface
                            )

                            Text(
                                text = it.job ?: "",
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.onSurface
                            )
                        }
                }

                Row(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    movieDetail?.runtime?.let {
                        Icon(
                            modifier = Modifier.size(14.dp),
                            painter = painterResource(id = R.drawable.ic_round_access_time),
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.size(8.dp))

                        Text(
                            text = "${movieDetail.runtime} min",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.size(8.dp))

            /**
             * Genre
             */

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
                    .horizontalScroll(genreScrollState),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                movieDetail?.genres?.forEach { genre ->
                    Chip(text = genre.name!!, bgColor = Color.Black.copy(alpha = 0.4f))
                }
            }

            movieDetail?.overview?.let {

                HeaderItem(
                    modifier = Modifier.padding(8.dp),
                    header = "Overview",
                    end = ""
                )

                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) { expandedOverView = !expandedOverView }
                        .animateContentSize(),
                    text = movieDetail.overview,
                    maxLines = if (expandedOverView) Int.MAX_VALUE else 5,
                    overflow = TextOverflow.Ellipsis
                )
            }

            HeaderItem(
                modifier = Modifier.padding(8.dp),
                header = "Cast", end = ""
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movieCredit?.cast?.take(10) ?: listOf()) {
                    CastingItem(
                        modifier = Modifier.width(150.dp),
                        cast = it
                    )
                }
            }

            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailPreview() {
    Scaffold {
        DetailContent(
            movieDetail = MovieDetail(
                overview = "Hardened ramen can be made thin by soaking with peanut sauce."
            ),
            movieCredit = MovieCredit(
                cast = listOf(
                    Cast(
                        name = "A",
                        character = "Z"
                    ),
                    Cast(
                        name = "A",
                        character = "Z"
                    )
                )
            ),
            isBooked = true
        )
    }
}
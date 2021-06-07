package com.github.psm.movie.review.ui.detail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.psm.movie.review.R
import com.github.psm.movie.review.ui.theme.Stared
import com.github.psm.movie.review.ui.widget.*
import com.github.psm.movie.review.utils.toImgUrl

@Composable
fun Detail(
    movieId: Int,
    navigateBack: (() -> Unit)? = null,
    detailViewModel: DetailViewModel = viewModel()
) {
    detailViewModel.getMovieDetail(movieId)
    val movieDetail by detailViewModel.movieDetail.observeAsState()
    val scrollState = rememberScrollState()
    var expandedOverView by remember { mutableStateOf(false) }
    val genreScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DetailAppBar(
            onBackClick = { navigateBack?.invoke() },
            scrollState = scrollState
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
                        .height(350.dp),
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
                    .padding(start = 16.dp, end = 16.dp)
                    .padding(top = 8.dp, bottom = 8.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Guard(
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
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = movieDetail?.title ?: "",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onSurface
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${movieDetail?.voteAverage ?: ""}",
                            modifier = Modifier.alpha(0.8f),
                            color = MaterialTheme.colors.onSurface
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Stared
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .horizontalScroll(genreScrollState),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                movieDetail?.genres?.forEach { genre ->
                    Chip(text = genre.name!!, bgColor = Color.Black.copy(alpha = 0.4f))
                }
            }

            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { expandedOverView = !expandedOverView }
                    .animateContentSize(),
                text = movieDetail?.overview ?: "",
                maxLines = if (expandedOverView) Int.MAX_VALUE else 5,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailPreview() {
    Detail(9999)
}
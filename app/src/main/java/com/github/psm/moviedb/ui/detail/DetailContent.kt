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
import com.github.psm.moviedb.R
import com.github.psm.moviedb.db.Resource
import com.github.psm.moviedb.db.model.genre.Genre
import com.github.psm.moviedb.db.model.shared.credit.Credit
import com.github.psm.moviedb.ui.home.HeaderItem
import com.github.psm.moviedb.ui.theme.Stared
import com.github.psm.moviedb.ui.widget.Chip
import com.github.psm.moviedb.ui.widget.DetailAppBar
import com.github.psm.moviedb.ui.widget.ErrorContent
import com.github.psm.moviedb.ui.widget.Gauge
import com.github.psm.moviedb.ui.widget.Image
import com.github.psm.moviedb.ui.widget.movie.CastingItem
import com.github.psm.moviedb.utils.isError
import com.github.psm.moviedb.utils.isLoading
import com.github.psm.moviedb.utils.toImgUrl
import com.valentinilk.shimmer.shimmer

@Composable
fun DetailContent(
    appBarTitle: String,
    /**
     * Detail
     */
    title: String?,
    posterPath: String?,
    backDropPath: String?,
    voteAverage: Double?,
    runtime: Int?,
    overview: String?,
    genres: List<Genre>?,

    /**
     * Credits
     */
    credit: Credit?,

    isBooked: Boolean,
    state: Resource<Any>,
    navigateBack: () -> Unit = { },
    onBookMarkClick: (booked: Boolean) -> Unit = { },
    navigateToPerson: (personId: Long) -> Unit = { },
    onRetry: () -> Unit = { }
) {
    var expandedOverView by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val genreScrollState = rememberScrollState()

    if (state.isError()) {
        return ErrorContent(onRetry = onRetry)
    } else if(state.isLoading()) {
        return DetailContentShimmer(appBarTitle = appBarTitle)
    }

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DetailAppBar(
            title = appBarTitle,
            onBackClick = navigateBack,
            scrollState = scrollState,
            onBookMarkClick = onBookMarkClick,
            bookmarkState = isBooked
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Box(
                modifier = Modifier.aspectRatio(16f / 9), contentAlignment = Alignment.BottomStart
            ) {

                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    data = backDropPath?.toImgUrl(),
                    contentScale = ContentScale.FillBounds,
                    enablePlaceHolder = false
                )

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(150.dp)
                        .aspectRatio(2f / 3),
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize(),
                        data = posterPath?.toImgUrl(),
                        contentScale = ContentScale.FillBounds,
                        fadeIn = true,
                        fadeInDurationMs = 2000,
                        enablePlaceHolder = true,
                    )
                }

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
                    value = ((voteAverage ?: 0.0) * 0.1).toFloat(),
                    bgColor = Color.Black,
                    strokeSize = 8f,
                    fontSize = 12.sp,
                    animated = true
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp), horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = title ?: "",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${voteAverage ?: ""}",
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
             * Director And Time
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
//                    credit?.crew?.find { it.department?.contains("Directing", true) == true }
//                        ?.let {
//                            Text(
//                                text = it.name ?: "",
//                                style = MaterialTheme.typography.body1,
//                                fontWeight = FontWeight.Bold,
//                                color = MaterialTheme.colors.onSurface
//                            )
//
//                            Text(
//                                text = it.job ?: "",
//                                style = MaterialTheme.typography.body2,
//                                color = MaterialTheme.colors.onSurface
//                            )
//                        }
                }

                Row(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    runtime?.let {
                        Icon(
                            modifier = Modifier.size(14.dp),
                            painter = painterResource(id = R.drawable.ic_round_access_time),
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.size(8.dp))

                        Text(
                            text = "$runtime min",
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
                genres?.forEach { genre ->
                    Chip(text = genre.name!!, bgColor = Color.Black.copy(alpha = 0.4f))
                }
            }

            overview?.let {

                HeaderItem(
                    modifier = Modifier.padding(8.dp), header = "Overview", end = ""
                )

                Text(modifier = Modifier
                    .padding(8.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(), indication = null
                    ) { expandedOverView = !expandedOverView }
                    .animateContentSize(),
                    text = overview,
                    maxLines = if (expandedOverView) Int.MAX_VALUE else 5,
                    overflow = TextOverflow.Ellipsis)
            }

            HeaderItem(
                modifier = Modifier.padding(8.dp), header = "Cast", end = ""
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(credit?.cast?.take(10) ?: listOf()) {
                    CastingItem(
                        modifier = Modifier.width(150.dp), cast = it, onCastClick = navigateToPerson
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
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            DetailContent(
                appBarTitle = "Tv",
                title = "Title",
                voteAverage = 100.0,
                posterPath = null,
                backDropPath = null,
                credit = null,
                genres = listOf(),
                overview = "Raptus visus sed mire imitaris sensorem est.",
                runtime = 150,
                isBooked = true,
                state = Resource.Initial
            )
        }
    }
}
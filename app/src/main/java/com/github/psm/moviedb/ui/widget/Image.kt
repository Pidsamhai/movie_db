package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.drawable.CrossfadeDrawable
import coil.request.ImageRequest
import coil.size.Scale
import com.github.psm.moviedb.R
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.valentinilk.shimmer.shimmer
import timber.log.Timber
import androidx.compose.foundation.Image as BaseImage


@OptIn(ExperimentalCoilApi::class)
@Composable
fun Image(
    modifier: Modifier = Modifier,
    data: String?,
    contentDescription: String? = null,
    fadeIn: Boolean = false,
    fadeInDurationMs: Int = CrossfadeDrawable.DEFAULT_DURATION,
    contentScale: ContentScale = ContentScale.FillBounds,
    enablePlaceHolder: Boolean = false
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .crossfade(fadeIn)
            .crossfade(fadeInDurationMs)
            .build(),
    )

    val enableShimmer = if (enablePlaceHolder)
        painter.state is AsyncImagePainter.State.Loading
    else false

    val isError = painter.state is AsyncImagePainter.State.Error

    BaseImage(
        modifier = modifier,
        painter = painter,
        contentDescription = contentDescription,
        contentScale = if (isError) ContentScale.None else contentScale
    )
}
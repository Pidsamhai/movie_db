package com.github.psm.moviedb.ui.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.drawable.CrossfadeDrawable
import coil.request.ImageRequest
import androidx.compose.foundation.Image as BaseImage


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
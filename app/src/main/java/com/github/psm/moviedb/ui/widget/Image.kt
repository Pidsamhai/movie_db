package com.github.psm.moviedb.ui.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.drawable.CrossfadeDrawable
import coil.size.Scale
import com.github.psm.moviedb.R
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
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
    val painter = rememberAsyncImagePainter(data)
//    {
////        model(data)
////        crossfade(fadeIn)
////        crossfade(fadeInDurationMs)
////        scale(Scale.FILL)
//        error(R.drawable.ic_round_broken_image)
//    }

    val enableShimmer = if (enablePlaceHolder)
        painter.state is AsyncImagePainter.State.Loading
    else false

    val isError = painter.state is AsyncImagePainter.State.Error

    BaseImage(
        modifier = modifier
            .placeholder(
                visible = enableShimmer,
                highlight = PlaceholderHighlight.fade(),
            ),
        painter = painter,
        contentDescription = contentDescription,
        contentScale = if (isError) ContentScale.None else contentScale
    )
}
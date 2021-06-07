package com.github.psm.movie.review.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.google.accompanist.imageloading.LoadPainterDefaults
import androidx.compose.foundation.Image as BaseImage


@Composable
fun Image(
    modifier: Modifier = Modifier,
    request: Any?,
    contentDescription: String? = null,
    fadeIn: Boolean = false,
    fadeInDurationMs: Int = LoadPainterDefaults.FadeInTransitionDuration,
    contentScale: ContentScale = ContentScale.Fit,
    loading: @Composable () -> Unit = {  },
    error: @Composable () -> Unit = { }
) {
    val painter = rememberCoilPainter(request = request, fadeIn = fadeIn, fadeInDurationMs = fadeInDurationMs)
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        when(painter.loadState) {
            ImageLoadState.Empty -> {
                error()
            }
            is ImageLoadState.Loading -> {
                loading()
            }
            is ImageLoadState.Error -> {
                error()
            }
            is ImageLoadState.Success -> {
                BaseImage(
                    modifier = modifier,
                    painter = painter,
                    contentDescription = contentDescription,
                    contentScale = contentScale
                )
            }
        }
    }
}
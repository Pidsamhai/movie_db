package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.psm.moviedb.R
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.google.accompanist.imageloading.LoadPainterDefaults
import com.google.accompanist.placeholder.material.placeholder
import androidx.compose.foundation.Image as BaseImage


@Composable
fun Image(
    modifier: Modifier = Modifier,
    request: Any?,
    contentDescription: String? = null,
    fadeIn: Boolean = false,
    fadeInDurationMs: Int = LoadPainterDefaults.FadeInTransitionDuration,
    contentScale: ContentScale = ContentScale.Fit,
    loading: @Composable () -> Unit = { },
    error: @Composable () -> Unit = { },
    enablePlaceHolder: Boolean = false
) {
    val painter =
        rememberCoilPainter(request = request, fadeIn = fadeIn, fadeInDurationMs = fadeInDurationMs)
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        when (painter.loadState) {
            ImageLoadState.Empty -> {
                error()
            }
            is ImageLoadState.Loading -> {
                loading()
            }
            is ImageLoadState.Error -> {
                error()
            }
            else -> Unit
        }
        BaseImage(
            modifier = modifier
                .apply {
                    if (enablePlaceHolder) {
                        placeholder(
                            visible = painter.loadState is ImageLoadState.Loading
                        )
                    }
                },
            painter = painter,
            contentDescription = contentDescription,
            contentScale = contentScale
        )
    }
}

@Composable
fun DefaultLoader() {
    Loader(size = 50.dp)
}

@Composable
fun DefaultError() {
    Icon(
        modifier = Modifier.size(50.dp),
        painter = painterResource(id = R.drawable.ic_round_broken_image),
        contentDescription = null,
        tint = LocalContentColor.current.copy(alpha = 0.5f)
    )
}
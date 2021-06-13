package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.github.psm.moviedb.R

sealed class LoaderStyle {
    object Dot: LoaderStyle()
    object Circular: LoaderStyle()
}

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    size: Dp = 16.dp,
    loaderStyle: LoaderStyle = LoaderStyle.Circular
) = LoaderContent(
    modifier = modifier,
    height = size,
    width = size,
    loaderStyle = loaderStyle
)

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    height: Dp,
    width: Dp,
    loaderStyle: LoaderStyle = LoaderStyle.Circular
) = LoaderContent(
    modifier = modifier,
    height = height,
    width = width,
    loaderStyle = loaderStyle
)

@Composable
private fun LoaderContent(
    modifier: Modifier = Modifier,
    height: Dp,
    width: Dp,
    loaderStyle: LoaderStyle
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when(loaderStyle) {
            LoaderStyle.Circular -> CircularProgressIndicator(Modifier.size(height))
            LoaderStyle.Dot -> {
                val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.loading) }
                val animationState = rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)
                LottieAnimation(
                    spec = animationSpec,
                    animationState = animationState,
                    modifier = Modifier.size(width = width, height = height)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoaderPreview() {
    Loader(size = 100.dp)
}
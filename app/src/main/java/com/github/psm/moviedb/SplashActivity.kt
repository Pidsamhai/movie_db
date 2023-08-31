package com.github.psm.moviedb

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.moviedb.ui.theme.MovieDbTheme
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieDbTheme {
                SplashContent(
                    end = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashContent(
    end: () -> Unit = { }
) {
    val minScale = 0.5f
    val defaultScale = 1f
    val maxScale = 50f
    var scale by remember { mutableFloatStateOf(defaultScale) }
    val animScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        finishedListener = {
            when (it) {
                maxScale -> end()
                minScale -> scale = maxScale
            }
        }, label = "Logo Animation"
    )

    LaunchedEffect(key1 = Unit) {
        delay(800)
        scale = minScale
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(size = 200.dp)
                .scale(animScale),
            painter = painterResource(R.drawable.ic_app_logo),
            contentDescription = null
        )
    }
}
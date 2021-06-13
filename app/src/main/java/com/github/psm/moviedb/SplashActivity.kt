package com.github.psm.moviedb

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.moviedb.ui.theme.MovieDbTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieDbTheme {
                SplashContent()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashContent() {
    val activity = (LocalContext.current as ComponentActivity)
    val infiniteTransition = rememberInfiniteTransition()
    val infiniteAnimateFloat = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    LaunchedEffect(key1 = null) {
        Handler(Looper.myLooper()!!).postDelayed({
            activity.startActivity(Intent(activity, MainActivity::class.java))
            activity.finishAffinity()
        }, 2500)
    }
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .alpha(infiniteAnimateFloat.value)
                        .background(Color(0XFF86CECB)),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = "Versions ${BuildConfig.VERSION_NAME}",
                style = MaterialTheme
                    .typography
                    .caption
                    .copy(
                        fontWeight = FontWeight.Bold
                    )
            )
        }
    }
}
package com.github.psm.movie.review

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.movie.review.ui.widget.Loader

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashContent() {
    val activity = (LocalContext.current as ComponentActivity)
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
                Loader(size = 50.dp)
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.body2
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
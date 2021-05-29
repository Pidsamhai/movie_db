package com.github.psm.movie.review

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.movie.review.ui.theme.MovieGrey
import com.github.psm.movie.review.ui.theme.MovieReviewTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieReviewTheme {
                MovieAppBody()
            }
        }
    }
}


@Composable
fun MovieAppBody() {
    Scaffold(
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Hi Edward",
                    style = MaterialTheme.typography.h4
                )
                Surface(
                    Modifier
                        .size(58.dp),
                    shape = CircleShape,
                ) {
                    Box(modifier = Modifier.background(MovieGrey))
                }
            }
        }
    ) { innerPadding ->
        NavGraph(Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true)
@Composable
fun MoveDefaultPreview() {
    MovieAppBody()
}


package com.github.psm.movie.review.ui.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.psm.movie.review.db.model.Result
import com.github.psm.movie.review.ui.widget.Guard
import com.github.psm.movie.review.utils.toImgUrl
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun MovieItem(result: Result, onClick: ((movieId: String) -> Unit)? = null) {
    Column(Modifier.width(150.dp)) {
        Surface(
            Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable { onClick?.invoke((result.id.toString())) }
        ) {
            Image(
                painter = rememberCoilPainter(
                    request = result.posterPath?.toImgUrl(),
                    fadeIn = true,
                    fadeInDurationMs = 3000
                ),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
            Box(modifier = Modifier
                .width(150.dp)
                .height(200.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Guard(
                    Modifier.size(60.dp)
                        .padding(4.dp)
                        .clip(CircleShape),
                    value = ((result.voteAverage ?: 0.0) * 0.1).toFloat(),
                    bgColor = Color.Black,
                    strokeSize = 8f,
                    fontSize = 12.sp
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = result.title ?: "",
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            repeat(5) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    MovieItem(Result(title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."))
}
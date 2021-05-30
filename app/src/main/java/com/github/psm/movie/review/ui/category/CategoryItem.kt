package com.github.psm.movie.review.ui.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.psm.movie.review.ui.theme.MovieBlue

@Composable
fun CategoryItem(text: String) {
    Surface(
        color = MovieBlue.copy(alpha = 0.2f),
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .clickable {

            }
    ) {
        Column(
            Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "\uD83D\uDE00", fontSize = 25.sp)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = text)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    CategoryItem("Horror")
}
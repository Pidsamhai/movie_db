package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Chip(
    text: String,
    bgColor: Color = Color.Black.copy(alpha = 0.6f)
) {
    Surface(
        modifier = Modifier
            .height(32.dp)
            .wrapContentWidth()
            .clip(CircleShape)
            .background(bgColor)
            .clickable {},
        shape = CircleShape,
        color = bgColor,
    ) {
        Box(
            modifier =
            Modifier.wrapContentWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.body2
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun ChipPreview() {
    Chip(text = "Horror")
}
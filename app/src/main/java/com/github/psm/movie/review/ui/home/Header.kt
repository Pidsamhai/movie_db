package com.github.psm.movie.review.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HeaderItem(
    modifier: Modifier = Modifier,
    header: String,
    end: String = "See more",
    onEndTextClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = header, style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold)
        Text(
            text = end,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .alpha(0.5f)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    onEndTextClick?.invoke()
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    HeaderItem(header = "Categories")
}
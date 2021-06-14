package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    value: String? = null,
    title: String,
) {
    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CompositionLocalProvider(
                LocalTextStyle provides MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
            ) {
                Text(
                    text = title
                )
                Spacer(modifier = Modifier.size(8.dp))
                value?.let {
                    Text(
                        text = it
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingItemPreview() {
    SettingItem(
        modifier = Modifier
            .size(100.dp)
            .clickable { },
        title = "Region"
    )
}
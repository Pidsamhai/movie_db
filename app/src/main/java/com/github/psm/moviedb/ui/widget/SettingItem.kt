package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    value: String? = null,
    title: String,
) {
    SettingContent(
        modifier = modifier
    ) {
        Text(
            text = title
        )
        Spacer(modifier = Modifier.size(8.dp))
        value?.let {
            Text(
                modifier = Modifier.padding(8.dp),
                text = it,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) = SettingContent(
    modifier = modifier,
    content = content
)

@Composable
private fun SettingContent(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
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
                content()
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
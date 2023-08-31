package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
        value?.let {
            Text(
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
    content: @Composable RowScope.() -> Unit
) = SettingContent(
    modifier = modifier,
    content = content
)

@Composable
private fun SettingContent(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
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
            .fillMaxWidth(),
        title = "Title",
        value = "Value"
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingItemPreviewWithContent() {
    SettingItem(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(text = "Clear Database")
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null
        )
    }
}


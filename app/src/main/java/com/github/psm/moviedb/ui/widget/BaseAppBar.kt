package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BaseAppBar(
    startContent: @Composable (scope: RowScope) -> Unit = { },
    centerContent: @Composable (scope: RowScope) -> Unit = { },
    endContent: @Composable (scope: RowScope) -> Unit = { },
) = BaseAppBarContent(
    startContent = startContent,
    centerContent = centerContent,
    endContent = endContent
)

@Composable
fun BaseAppBar(
    startContent: @Composable (scope: RowScope) -> Unit = { },
    title: String,
    endContent: @Composable (scope: RowScope) -> Unit = { },
) {
    val fontStyle = MaterialTheme
        .typography
        .h6
        .copy(
            fontSize = 20.sp
        )
    return BaseAppBarContent(
        startContent = startContent,
        centerContent = {
            CompositionLocalProvider(LocalTextStyle provides fontStyle) {
                Text(text = title)
            }
        },
        endContent = endContent
    )
}

@Composable
private fun BaseAppBarContent(
    startContent: @Composable (scope: RowScope) -> Unit = { },
    centerContent: @Composable (scope: RowScope) -> Unit = { },
    endContent: @Composable (scope: RowScope) -> Unit = { },
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.defaultMinSize(48.dp)
            ) {
                startContent(this)
            }
            Row(
                modifier = Modifier
                    .weight(1f, true),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                centerContent(this)
            }
            Row(
                modifier = Modifier.defaultMinSize(48.dp)
            ) {
                endContent(this)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun BaseAppBarPreview() {
    BaseAppBar(
        startContent = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        title = "Base AppBar"
    )
}
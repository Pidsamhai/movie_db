package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
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
    endContent = endContent,
    isScrolled = false
)

@Composable
fun BaseAppBar(
    startContent: @Composable (scope: RowScope) -> Unit = { },
    title: String,
    endContent: @Composable (scope: RowScope) -> Unit = { },
    enableShadow: Boolean = false
) = BaseAppBarText(
    title = title,
    startContent = startContent,
    endContent = endContent,
    isScrolled = enableShadow
)

@Composable
fun BaseAppBar(
    startContent: @Composable (scope: RowScope) -> Unit = { },
    title: String,
    endContent: @Composable (scope: RowScope) -> Unit = { },
    scrollState: ScrollState? = null
) = BaseAppBarText(
    title = title,
    startContent = startContent,
    endContent = endContent,
    isScrolled = scrollState?.value ?: 0 > 10
)

@Composable
fun BaseAppBar(
    startContent: @Composable (scope: RowScope) -> Unit = { },
    title: String,
    endContent: @Composable (scope: RowScope) -> Unit = { },
    listState: LazyListState?
) {
    val isScrolled = if (listState?.firstVisibleItemIndex == 0)
        listState.firstVisibleItemScrollOffset > 10
    else true
    BaseAppBarText(
        title = title,
        startContent = startContent,
        endContent = endContent,
        isScrolled = isScrolled
    )
}

@Composable
private fun BaseAppBarText(
    startContent: @Composable (scope: RowScope) -> Unit = { },
    title: String,
    endContent: @Composable (scope: RowScope) -> Unit = { },
    isScrolled: Boolean
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
        endContent = endContent,
        isScrolled = isScrolled
    )
}

@Composable
fun BaseAppBar(
    startContent: @Composable (scope: RowScope) -> Unit = { },
    centerContent: @Composable (scope: RowScope) -> Unit = { },
    endContent: @Composable (scope: RowScope) -> Unit = { },
    scrollState: ScrollState? = null,
) {
    BaseAppBarContent(
        startContent = startContent,
        centerContent = centerContent,
        endContent = endContent,
        isScrolled = scrollState?.value ?: 0 > 10
    )
}

@Composable
fun BaseAppBar(
    startContent: @Composable (scope: RowScope) -> Unit = { },
    centerContent: @Composable (scope: RowScope) -> Unit = { },
    endContent: @Composable (scope: RowScope) -> Unit = { },
    listState: LazyListState? = null,
) {
    val isScrolled = if (listState?.firstVisibleItemIndex == 0)
        listState.firstVisibleItemScrollOffset > 10
    else true
    BaseAppBarContent(
        startContent = startContent,
        centerContent = centerContent,
        endContent = endContent,
        isScrolled = isScrolled
    )
}

@Composable
private fun BaseAppBarContent(
    startContent: @Composable (scope: RowScope) -> Unit = { },
    centerContent: @Composable (scope: RowScope) -> Unit = { },
    endContent: @Composable (scope: RowScope) -> Unit = { },
    isScrolled: Boolean
) {
    TopAppBar(
        elevation = if (isScrolled) AppBarDefaults.TopAppBarElevation else 0.dp,
        backgroundColor = MaterialTheme.colors.surface
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


@Preview(showBackground = true)
@Composable
private fun BaseAppBarPreview() {
    BaseAppBar(
        enableShadow = false,
        startContent = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        title = "Base AppBar"
    )
}

@Preview(showBackground = true)
@Composable
private fun BaseAppBarEndIconPreview() {
    BaseAppBar(
        enableShadow = false,
        startContent = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        title = "Base AppBar",
        endContent = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        }
    )
}
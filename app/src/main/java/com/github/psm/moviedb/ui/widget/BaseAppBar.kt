package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BaseAppBar(
    startContent: @Composable (scope: RowScope) -> Unit = { },
    centerContent: @Composable (scope: RowScope) -> Unit = {  },
    endContent: @Composable (scope: RowScope) -> Unit = {  },
) = BaseAppBarContent(
    startContent,
    centerContent,
    endContent,
)

@Composable
fun BaseAppBar(
    startContent: @Composable (scope: RowScope) -> Unit = { },
    centerContent: @Composable (scope: RowScope) -> Unit = {  },
    endContent: @Composable (scope: RowScope) -> Unit = {  },
    scrollState: ScrollState? = null,
) = BaseAppBarContent(
    startContent,
    centerContent,
    endContent,
    scrolled = scrollState?.value ?: 0 > 10
)

@Composable
fun BaseAppBar(
    startContent: @Composable (scope: RowScope) -> Unit = { },
    centerContent: @Composable (scope: RowScope) -> Unit = {  },
    endContent: @Composable (scope: RowScope) -> Unit = {  },
    listState: LazyListState? = null
) {
    val scrolled = if (listState?.firstVisibleItemIndex == 0)
        listState.firstVisibleItemScrollOffset > 10
    else true

    BaseAppBarContent(
        startContent,
        centerContent,
        endContent,
        scrolled =  scrolled
    )
}

@Composable
private fun BaseAppBarContent(
    startContent: @Composable (scope: RowScope) -> Unit = { },
    centerContent: @Composable (scope: RowScope) -> Unit = {  },
    endContent: @Composable (scope: RowScope) -> Unit = {  },
    scrolled: Boolean = false
) {
    Surface(
        elevation = if (scrolled) 8.dp else 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.defaultMinSize(48.dp)
            ) {
                startContent.invoke(this)
            }
            Row(
                modifier = Modifier
                    .weight(1f, true),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                centerContent.invoke(this)
            }
            Row(
                modifier = Modifier.defaultMinSize(48.dp)
            ) {
                endContent.invoke(this)
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
        centerContent = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight())
        }
    )
}
package com.github.psm.movie.review.ui.widget

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
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
    scrollState: ScrollState? = null
) {

    Surface(
        elevation = if (scrollState?.value ?: 0 > 10) 8.dp else 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
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
                Modifier.defaultMinSize(48.dp)
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
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight())
        }
    )
}
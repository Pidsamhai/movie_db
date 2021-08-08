package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.ScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.moviedb.R

@Composable
fun DetailAppBar(
    onBackClick: () -> Unit = {  },
    onBookMarkClick: (booked:Boolean) -> Unit = {  },
    scrollState: ScrollState? = null,
    bookmarkState: Boolean = false,
    backgroundColor: Color? = null
) {

    TopAppBar(
        elevation = if (scrollState?.value ?: 0 > 10) 8.dp else 0.dp,
        backgroundColor = backgroundColor ?: MaterialTheme.colors.surface
    ) {
        IconButton(onClick = { onBackClick.invoke() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }

        Text(
            modifier = Modifier.weight(1f),
            text = "Detail Movie", fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        IconButton(
            onClick = {
                onBookMarkClick(!bookmarkState)
            }
        ) {
            Icon(
                painter = painterResource(
                    id = if (bookmarkState) R.drawable.ic_outline_bookmark_24
                    else R.drawable.ic_outline_bookmark_border_24
                ),
                contentDescription = null
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DetailAppBarPreview() {
    DetailAppBar()
}
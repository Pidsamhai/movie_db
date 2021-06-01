package com.github.psm.movie.review.ui.widget

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.movie.review.R

@Composable
fun DetailAppBar(
    onBackClick: () -> Unit = {  },
    onBookMarkClick: (booked:Boolean) -> Unit = {  },
    scrollState: ScrollState? = null
) {
    var bookmarkState by remember { mutableStateOf(false) }

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
            IconButton(onClick = { onBackClick.invoke() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }

            Text(text = "Detail Movie", fontWeight = FontWeight.Bold)

            IconButton(
                onClick = {
                    bookmarkState = !bookmarkState
                    onBookMarkClick.invoke(bookmarkState)
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
}


@Preview(showBackground = true)
@Composable
private fun DetailAppBarPreview() {
    DetailAppBar()
}
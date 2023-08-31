package com.github.psm.moviedb.ui.widget

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.psm.moviedb.ui.theme.LogoFont

private object DefaultProfileSize {
    val collapse = 30.dp
    val expanded = 45.dp
}

private object DefaultLogoText {
    val collapse = LogoFont.copy(fontSize = 24.sp)
    val expanded = LogoFont.copy(fontSize = 40.sp)
}

private object DefaultElevation {
    val collapse = 8.dp
    val expanded = 0.dp
}

private const val DEFAULT_APPBAR_SCROLL_OFFSET = 50

@Composable
fun CustomAppBar(
    scrollState: ScrollState? = null,
    searchClick: () -> Unit = { }
) {
    var profileSize by remember { mutableStateOf(DefaultProfileSize.expanded) }
    var textStyle by remember { mutableStateOf(DefaultLogoText.expanded) }
    var elevation by remember { mutableStateOf(DefaultElevation.expanded) }
    val scrollOffset = scrollState?.value ?: 0

    if (scrollOffset > DEFAULT_APPBAR_SCROLL_OFFSET) {
        profileSize = DefaultProfileSize.collapse
        textStyle = DefaultLogoText.collapse
        elevation = DefaultElevation.collapse
    } else {
        profileSize = DefaultProfileSize.expanded
        textStyle = DefaultLogoText.expanded
        elevation = DefaultElevation.expanded
    }

    val profileAnimState = animateDpAsState(targetValue = profileSize)

    Surface(
        elevation = elevation
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .defaultMinSize(minWidth = 100.dp)
                    .weight(1f),
                text = "MOVIE DB",
                style = textStyle,
                textAlign = TextAlign.Left
            )

            IconButton(
                modifier = Modifier
                    .size(profileAnimState.value),
                onClick = { searchClick() }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun CustomAppBar() {
    CustomAppBar()
}
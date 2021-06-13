package com.github.psm.moviedb.ui.widget

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.psm.moviedb.ui.theme.MovieGrey

@Composable
fun CustomAppBar(
    scrollState: ScrollState? = null,
) {
    var profileSize by remember { mutableStateOf(58.dp) }
    var appBarPadding by remember { mutableStateOf(24.dp) }

    var isBigTitleText by remember { mutableStateOf(true) }
    if (scrollState?.value ?: 0 > 50) {
        profileSize = 30.dp
        isBigTitleText = false
        appBarPadding = 16.dp
    } else {
        profileSize = 58.dp
        isBigTitleText = true
        appBarPadding = 24.dp
    }

    val profileAnimState = animateDpAsState(targetValue = profileSize)
    val appbarPaddingAnimState = animateDpAsState(targetValue = appBarPadding)

    Surface(
        elevation = if (isBigTitleText) 0.dp else 8.dp
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(appbarPaddingAnimState.value),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Hi Edward",
                style = if (isBigTitleText) MaterialTheme.typography.h6.copy(fontSize = 32.sp)
                else MaterialTheme.typography.h6.copy(fontSize = 20.sp),
                modifier = Modifier
                    .defaultMinSize(minWidth = 100.dp)
//                .animateContentSize()
                ,
                textAlign = TextAlign.Right
            )
            Surface(
                Modifier
                    .size(profileAnimState.value),
                shape = CircleShape,
            ) {
                Box(modifier = Modifier.background(MovieGrey))
            }
        }
    }
}
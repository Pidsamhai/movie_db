package com.github.psm.moviedb.utils

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable


fun LazyListScope.placeholder(
    enable: () -> Boolean,
    itemCount: Int,
    content: @Composable () -> Unit
) {
    if (enable()) {
        items(itemCount) {
            content()
        }
    }
}
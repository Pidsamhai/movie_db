package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InnerScaffold(
    modifier: Modifier,
    appBar: @Composable () -> Unit,
    innerPaddingValues: PaddingValues = PaddingValues(),
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
    ) {
        appBar()
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPaddingValues)
        ) {
            content()
        }
    }
}
package com.github.psm.moviedb.utils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType


//@ExperimentalFoundationApi
//fun <T : Any> LazyGridScope.items(
//    lazyPagingItems: LazyPagingItems<T>,
//    itemContent: @Composable LazyItemScope.(value: T?) -> Unit
//) {
//    items(lazyPagingItems.itemCount) { index ->
//        itemContent(lazyPagingItems[index])
//    }
//}
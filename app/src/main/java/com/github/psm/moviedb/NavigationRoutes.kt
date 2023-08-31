package com.github.psm.moviedb

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

sealed class NavigationRoutes(
    val route: String,
    val icon: @Composable (() -> Unit)? = null,
    val label: String,
    val isTopLevel: Boolean = false
) {
    data object Home: NavigationRoutes(
        route = "home",
        icon = {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "home"
            )
        },
        label = "Home",
        isTopLevel = true
    )
    data object Detail: NavigationRoutes(route = "detail", label = "Detail")
    data object About: NavigationRoutes(
        route = "about",
        icon = {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "about"
            )
        },
        label = "About"
    )
    data object SearchPage: NavigationRoutes(route = "search", label = "Search")
    data object Popular: NavigationRoutes(route = "popular", label = "Popular")
    data object UpComing: NavigationRoutes(route = "upcoming", label = "Up Coming")
    data object BookmarkPage: NavigationRoutes(
        route = "bookmark",
        label = "Bookmark",
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_outline_bookmark_24),
                contentDescription = "about"
            )
        }
    )
    data object Person: NavigationRoutes(route = "person", label = "Person")
}
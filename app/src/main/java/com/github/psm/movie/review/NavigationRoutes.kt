package com.github.psm.movie.review

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable

sealed class NavigationRoutes(
    val route: String,
    val icon: @Composable (() -> Unit)? = null,
    val label: String,
    val isTopLevel: Boolean = false
) {
    object Home: NavigationRoutes(
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
    object Detail: NavigationRoutes(route = "detail", label = "Detail")
    object About: NavigationRoutes(
        route = "about",
        icon = {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "about"
            )
        },
        label = "About"
    )
    object SearchPage: NavigationRoutes(route = "search", label = "Search")
}
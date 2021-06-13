package com.github.psm.moviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.psm.moviedb.ui.theme.MovieDbTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieDbTheme {
                MovieAppBody()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovieAppBody() {
    val navController = rememberNavController()
    val topLevelNavigationRoutes = listOf<NavigationRoutes>(
        NavigationRoutes.Home,
        NavigationRoutes.About
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (topLevelNavigationRoutes.contain(currentRoute))
                MyBottomNavigation(
                    topLevelNavigationRoutes = topLevelNavigationRoutes,
                    navController = navController,
                    currentRoute = currentRoute
                )
        }
    ) { innerPadding ->
        NavGraph(Modifier.padding(innerPadding), navController)
    }
}

fun List<NavigationRoutes>.contain(route: String?): Boolean {
    return find { it.route == route } != null
}

@Composable
fun MyBottomNavigation(
    topLevelNavigationRoutes: List<NavigationRoutes>,
    navController: NavHostController,
    currentRoute: String?
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface
    ) {
        topLevelNavigationRoutes.forEach { route ->
            val selected = currentRoute == route.route
            BottomNavigationItem(
                selected = selected,
                onClick = {
                    navController.navigate(route.route) {
                        popUpTo(
                            navController.graph.startDestinationRoute
                                ?: NavigationRoutes.Home.route
                        ) { saveState = true }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true

                    }
                },
                label = if (selected) {
                    { Text(text = "•", fontSize = 24.sp) }
                } else null,
                icon = route.icon!!
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoveDefaultPreview() {
    MovieAppBody()
}


package com.github.psm.movie.review

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.psm.movie.review.ui.theme.MovieReviewTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieReviewTheme {
                MovieAppBody()
            }
        }
    }
}

@Composable
fun MovieAppBody() {
    val navController = rememberNavController()
    val topLevelNavigationRoutes = listOf<NavigationRoutes>(
        NavigationRoutes.Home,
        NavigationRoutes.About
    )
    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                topLevelNavigationRoutes.forEach { route ->
                    BottomNavigationItem(
                        selected = currentRoute == route.route,
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
                        label = { Text(text = route.label) },
                        icon = route.icon!!
                    )
                }
            }
        }
    ) { innerPadding ->
        NavGraph(Modifier.padding(innerPadding), navController)
    }
}

@Preview(showBackground = true)
@Composable
fun MoveDefaultPreview() {
    MovieAppBody()
}


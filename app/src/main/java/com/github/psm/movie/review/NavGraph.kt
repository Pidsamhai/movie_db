package com.github.psm.movie.review

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.psm.movie.review.ui.home.Home
import com.github.psm.movie.review.ui.home.HomeViewModel


object MainDestinations {
    const val HOME_ROUTE = "home"
}


@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
//    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainDestinations.HOME_ROUTE
    ) {
        composable(MainDestinations.HOME_ROUTE) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            Home(selectMovie = {}, homeViewModel = homeViewModel)
        }
    }
}
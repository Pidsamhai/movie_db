package com.github.psm.moviedb

import androidx.navigation.NavHostController

class NavActions(private val navController: NavHostController) {
    fun navigateToDetail(movieId: Long) = navController.navigate("${NavigationRoutes.Detail.route}/$movieId")
    fun navigateUp() = navController.navigateUp()
    fun navigateToSearch() = navController.navigate(NavigationRoutes.SearchPage.route)
    fun navigateToUpComing() = navController.navigate(NavigationRoutes.UpComing.route)
    fun navigateToPopular() = navController.navigate(NavigationRoutes.Popular.route)
}
package com.github.psm.moviedb

import androidx.navigation.NavHostController

class NavActions(private val navController: NavHostController) {
    fun navigateToMovieDetail(id: Long) = navController.navigate("${NavigationRoutes.Detail.route}/movie/$id")
    fun navigateToTvDetail(id: Long) = navController.navigate("${NavigationRoutes.Detail.route}/tv/$id")
    fun navigateUp() = navController.navigateUp()
    fun navigateToSearch() = navController.navigate(NavigationRoutes.SearchPage.route)
    fun navigateToUpComing() = navController.navigate(NavigationRoutes.UpComing.route)
    fun navigateToPopular() = navController.navigate(NavigationRoutes.Popular.route)
    fun navigateToPerson(personId: Long) = navController.navigate("${NavigationRoutes.Person.route}/$personId")
}
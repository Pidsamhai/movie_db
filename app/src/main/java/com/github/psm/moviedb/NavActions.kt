package com.github.psm.moviedb

import androidx.navigation.NavHostController

class NavActions(private val navController: NavHostController) {
    fun navigateToMovieDetail(id: Long) = baseNavigate("${NavigationRoutes.Detail.route}/movie/$id")
    fun navigateToTvDetail(id: Long) = baseNavigate("${NavigationRoutes.Detail.route}/tv/$id")
    fun navigateUp() = navController.navigateUp()
    fun navigateToSearch() = baseNavigate(NavigationRoutes.SearchPage.route)
    fun navigateToUpComing() = baseNavigate(NavigationRoutes.UpComing.route)
    fun navigateToPopular() = baseNavigate(NavigationRoutes.Popular.route)
    fun navigateToPerson(personId: Long) = baseNavigate("${NavigationRoutes.Person.route}/$personId")

    private fun baseNavigate(
        route: String
    ) {
        navController.navigate(route = route) {
            restoreState = true
            launchSingleTop = true
        }
    }
}
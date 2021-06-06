package com.github.psm.movie.review

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.github.psm.movie.review.ui.about.About
import com.github.psm.movie.review.ui.detail.Detail
import com.github.psm.movie.review.ui.detail.DetailViewModel
import com.github.psm.movie.review.ui.home.Home
import com.github.psm.movie.review.ui.home.HomeViewModel
import com.github.psm.movie.review.ui.search.SearchPage
import com.github.psm.movie.review.utils.rawRoute
import timber.log.Timber

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
//    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    onTopLevelDestination: ((isTopLevel: Boolean) -> Unit) ? = null
) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
        onTopLevelDestination?.invoke(destination.route == NavigationRoutes.Home.route)
        Timber.i("Destination: ${destination.rawRoute}")
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationRoutes.Home.route
    ) {
        composable(NavigationRoutes.Home.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            Home(
                homeViewModel = homeViewModel,
                navigateToSearchPage = {
                    navController.navigate(NavigationRoutes.SearchPage.route)
                }
            ) { movieId ->
                navController.navigate("${NavigationRoutes.Detail.route}/$movieId")
            }
        }

        composable(
            route = "${NavigationRoutes.Detail.route}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType }),
        ) { backStack ->
            val viewModel = hiltViewModel<DetailViewModel>()
            Detail(
                movieId = backStack.arguments?.getInt("movieId")!!,
                detailViewModel = viewModel,
                navigateBack = { navController.navigateUp() }
            )
        }

        composable(route = NavigationRoutes.About.route) { About() }

        composable(route = NavigationRoutes.SearchPage.route) {

            SearchPage(
                onBackPress = { navController.navigateUp() },
                onItemClick = { movieId ->
                    navController.navigate("${NavigationRoutes.Detail.route}/$movieId") {
                        popUpTo(NavigationRoutes.Home.route)
                    }
                }
            )
        }
    }
}
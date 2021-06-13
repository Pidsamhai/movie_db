package com.github.psm.moviedb

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.github.psm.moviedb.ui.about.About
import com.github.psm.moviedb.ui.bookmark.BookmarkPage
import com.github.psm.moviedb.ui.detail.Detail
import com.github.psm.moviedb.ui.detail.DetailViewModel
import com.github.psm.moviedb.ui.home.Home
import com.github.psm.moviedb.ui.home.HomeViewModel
import com.github.psm.moviedb.ui.movielist.MovieListPage
import com.github.psm.moviedb.ui.popular.PopularVM
import com.github.psm.moviedb.ui.search.SearchPage
import com.github.psm.moviedb.ui.upcoming.UpComingVM
import com.github.psm.moviedb.utils.rawRoute
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
                },
                selectMovie = { movieId ->
                    navController.navigate("${NavigationRoutes.Detail.route}/$movieId")
                },
                navigateToPopular = {
                    navController.navigate(NavigationRoutes.Popular.route)
                },
                navigateToUpComing = {
                    navController.navigate(NavigationRoutes.UpComing.route)
                }
            )
        }

        composable(
            route = "${NavigationRoutes.Detail.route}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.LongType }),
        ) { backStack ->
            val viewModel = hiltViewModel<DetailViewModel>()
            Detail(
                movieId = backStack.arguments?.getLong("movieId")!!,
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

        composable(route = NavigationRoutes.Popular.route) {
            MovieListPage(
                title = NavigationRoutes.Popular.label,
                viewModel = hiltViewModel<PopularVM>(),
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(route = NavigationRoutes.UpComing.route) {
            MovieListPage(
                title = NavigationRoutes.UpComing.label,
                viewModel = hiltViewModel<UpComingVM>(),
                onBackClick = { navController.navigateUp() },
                selectedMovie = { movieId ->
                    navController.navigate("${NavigationRoutes.Detail.route}/$movieId")
                }
            )
        }

        composable(route = NavigationRoutes.BookmarkPage.route) {
            BookmarkPage(
                title = NavigationRoutes.BookmarkPage.label
            )
        }
    }
}
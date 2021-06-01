package com.github.psm.movie.review

import android.util.Log
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
import com.github.psm.movie.review.utils.rawRoute

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
//    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    onTopLevelDestination: ((isTopLevel: Boolean) -> Unit) ? = null
) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
        onTopLevelDestination?.invoke(destination.route == NavigationRoutes.Home.route)
        Log.i("TAG", "NavGraph: ${destination.rawRoute}")
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationRoutes.Home.route
    ) {
        composable(NavigationRoutes.Home.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            Home(homeViewModel = homeViewModel) { movieId ->
                navController.navigate("${NavigationRoutes.Detail.route}/$movieId")
            }
        }

        composable(
            route = "${NavigationRoutes.Detail.route}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.StringType }),
        ) { backStack ->
            val viewModel = hiltViewModel<DetailViewModel>()
            Detail(
                movieId = backStack.arguments?.getString("movieId")!!,
                detailViewModel = viewModel,
                navigateBack = { navController.navigateUp() }
            )
        }

        composable(route = NavigationRoutes.About.route) { About() }
    }
}
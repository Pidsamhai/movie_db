package com.github.psm.moviedb

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.github.psm.moviedb.ui.about.About
import com.github.psm.moviedb.ui.bookmark.BookmarkPage
import com.github.psm.moviedb.ui.detail.Detail
import com.github.psm.moviedb.ui.detail.DetailViewModel
import com.github.psm.moviedb.ui.home.Home
import com.github.psm.moviedb.ui.home.HomeViewModel
import com.github.psm.moviedb.ui.movielist.MovieListPage
import com.github.psm.moviedb.ui.person.PersonPage
import com.github.psm.moviedb.ui.popular.PopularVM
import com.github.psm.moviedb.ui.search.SearchPage
import com.github.psm.moviedb.ui.upcoming.UpComingVM
import com.github.psm.moviedb.utils.rawRoute
import timber.log.Timber

private const val BASE_URL = "https://www.themoviedb.org"

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onTopLevelDestination: ((isTopLevel: Boolean) -> Unit) ? = null
) {
    val actions = remember { NavActions(navController) }
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
                navigateToSearchPage = { actions.navigateToSearch() },
                selectMovie = { movieId -> actions.navigateToDetail(movieId) },
                navigateToPopular = { actions.navigateToPopular() },
                navigateToUpComing = { actions.navigateToUpComing() }
            )
        }

        composable(
            route = "${NavigationRoutes.Detail.route}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.LongType }),
            deepLinks = listOf(
                navDeepLink { uriPattern = "$BASE_URL/movie/{movieId}-.*" },
//                navDeepLink { uriPattern = "$BASE_URL/tv/{movieId}-.*" },
            )
        ) { backStack ->
            val viewModel = hiltViewModel<DetailViewModel>()
            Detail(
                movieId = backStack.arguments?.getLong("movieId")!!,
                detailViewModel = viewModel,
                navigateBack = { actions.navigateUp() },
                navigateToPerson = {  actions.navigateToPerson(it) }
            )
        }

        composable(route = NavigationRoutes.About.route) { About() }

        composable(route = NavigationRoutes.SearchPage.route) {
            SearchPage(
                onBackPress = { actions.navigateUp() },
                onItemClick = { movieId -> actions.navigateToDetail(movieId) }
            )
        }

        composable(route = NavigationRoutes.Popular.route) {
            MovieListPage(
                title = NavigationRoutes.Popular.label,
                viewModel = hiltViewModel<PopularVM>(),
                onBackClick = { actions.navigateUp() },
                selectedMovie = { movieId -> actions.navigateToDetail(movieId) }
            )
        }

        composable(route = NavigationRoutes.UpComing.route) {
            MovieListPage(
                title = NavigationRoutes.UpComing.label,
                viewModel = hiltViewModel<UpComingVM>(),
                onBackClick = { actions.navigateUp() },
                selectedMovie = { movieId -> actions.navigateToDetail(movieId) }
            )
        }

        composable(route = NavigationRoutes.BookmarkPage.route) {
            BookmarkPage(
                navigateToDetailPage = { movieId ->  actions.navigateToDetail(movieId) }
            )
        }

        composable(
            route = "${NavigationRoutes.Person.route}/{personId}",
            arguments = listOf(navArgument("personId") { type = NavType.LongType }),
            deepLinks = listOf(navDeepLink { uriPattern = "$BASE_URL/person/{personId}-.*" })
        ) { backStack ->
            PersonPage(
                viewModel = hiltViewModel(backStackEntry = backStack),
                navigateBack = { actions.navigateUp() },
                navigateToMovieDetail = { actions.navigateToDetail(it) }
            )
        }
    }
}
package com.github.psm.moviedb

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.github.psm.moviedb.ui.about.About
import com.github.psm.moviedb.ui.bookmark.BookmarkPage
import com.github.psm.moviedb.ui.detail.MovieDetailPage
import com.github.psm.moviedb.ui.detail.TvDetailPage
import com.github.psm.moviedb.ui.home.Home
import com.github.psm.moviedb.ui.movielist.MovieListPage
import com.github.psm.moviedb.ui.person.PersonPage
import com.github.psm.moviedb.ui.popular.PopularVM
import com.github.psm.moviedb.ui.search.SearchPage
import com.github.psm.moviedb.ui.upcoming.UpComingVM
import timber.log.Timber

private const val BASE_URL = "https://www.themoviedb.org"

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val actions = remember { NavActions(navController) }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationRoutes.Home.route
    ) {
        composable(NavigationRoutes.Home.route) {
            Home(
                navigateToSearchPage = { actions.navigateToSearch() },
                selectMovie = { id ->
                    Timber.d("Movie Id", id)
                    actions.navigateToMovieDetail(id) },
                selectTv = { id ->
                    Timber.d("TV Id", id)
                    actions.navigateToTvDetail(id)
                },
                navigateToPopular = { actions.navigateToPopular() },
                navigateToUpComing = { actions.navigateToUpComing() }
            )
        }

        composable(
            route = "${NavigationRoutes.Detail.route}/movie/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType }),
            deepLinks = listOf(
                navDeepLink { uriPattern = "$BASE_URL/movie/{id}-.*" },
            )
        ) { _ ->
            MovieDetailPage(
                navigateBack = { actions.navigateUp() },
                navigateToPerson = { actions.navigateToPerson(it) }
            )
        }

        composable(route = NavigationRoutes.About.route) { About() }

        composable(route = NavigationRoutes.SearchPage.route) {
            SearchPage(
                onBackPress = { actions.navigateUp() },
                selectedMovie = { id -> actions.navigateToMovieDetail(id) },
                selectedTv = { id -> actions.navigateToTvDetail(id) }
            )
        }

        composable(route = NavigationRoutes.Popular.route) {
            MovieListPage(
                title = NavigationRoutes.Popular.label,
                viewModel = hiltViewModel<PopularVM>(),
                onBackClick = { actions.navigateUp() },
                selectedMovie = { movieId -> actions.navigateToMovieDetail(movieId) }
            )
        }

        composable(route = NavigationRoutes.UpComing.route) {
            MovieListPage(
                title = NavigationRoutes.UpComing.label,
                viewModel = hiltViewModel<UpComingVM>(),
                onBackClick = { actions.navigateUp() },
                selectedMovie = { movieId -> actions.navigateToMovieDetail(movieId) }
            )
        }

        composable(route = NavigationRoutes.BookmarkPage.route) {
            BookmarkPage(
                navigateToMovieDetail = { id -> actions.navigateToMovieDetail(id) },
                navigateToTvDetail = { id -> actions.navigateToTvDetail(id) },
            )
        }

        composable(
            route = "${NavigationRoutes.Person.route}/{personId}",
            arguments = listOf(navArgument("personId") { type = NavType.LongType }),
            deepLinks = listOf(navDeepLink { uriPattern = "$BASE_URL/person/{personId}-.*" })
        ) { _ ->
            PersonPage(
                navigateBack = { actions.navigateUp() },
                navigateToMovieDetail = { actions.navigateToMovieDetail(it) }
            )
        }

        composable(
            route = "${NavigationRoutes.Detail.route}/tv/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType }),
            deepLinks = listOf(
                navDeepLink { uriPattern = "$BASE_URL/tv/{id}" },
            )
        ) { _ ->
            TvDetailPage(
                navigateBack = { actions.navigateUp() },
                navigateToPerson = { actions.navigateToPerson(it) }
            )
        }
    }
}
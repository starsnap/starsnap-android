package com.photo.starsnap.main.route.bottom_nav_route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.photo.starsnap.main.ui.screen.main.search.SearchScreen
import com.photo.starsnap.main.utils.NavigationRoute

@Composable
fun SearchRoute(navController: NavController) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.SEARCH,
        route = NavigationRoute.SEARCH_ROUTE
    ) {
        composable(NavigationRoute.SEARCH) {
            SearchScreen(navController)
        }
    }
}
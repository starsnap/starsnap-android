package com.photo.starsnap.main.route.bottom_nav_route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.photo.starsnap.main.ui.screen.main.search.SearchScreen
import com.photo.starsnap.main.utils.NavigationRoute

fun NavGraphBuilder.SearchRoute(navController: NavController) {
    navigation(startDestination = NavigationRoute.SEARCH, route = NavigationRoute.SEARCH_ROUTE) {
        composable(NavigationRoute.SEARCH) {
            SearchScreen(navController)
        }
    }
}
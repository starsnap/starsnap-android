package com.photo.starsnap.main.route.bottom_nav_route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.photo.starsnap.main.ui.screen.main.star_hub.StarGroupListScreen
import com.photo.starsnap.main.ui.screen.main.star_hub.StarListScreen
import com.photo.starsnap.main.utils.NavigationRoute

fun NavGraphBuilder.StarHubRoute(navController: NavController) {
    navigation(
        startDestination = NavigationRoute.LOGIN,
        route = NavigationRoute.STAR_HUB_ROUTE
    ) {
        composable(NavigationRoute.STAR_LIST) {
            StarListScreen(navController)
        }
        composable(NavigationRoute.STAR_GROUP_LIST) {
            StarGroupListScreen(navController)
        }
    }
}
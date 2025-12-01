package com.photo.starsnap.main.route.bottom_nav_route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.photo.starsnap.main.ui.screen.main.star_hub.SearchHubScreen
import com.photo.starsnap.main.utils.NavigationRoute
import com.photo.starsnap.main.viewmodel.main.StarViewModel

@Composable
fun StarHubRoute(navController: NavController, starViewModel: StarViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.STAR_HUB,
        route = NavigationRoute.STAR_HUB_ROUTE
    ) {
        composable(NavigationRoute.STAR_HUB) {
            SearchHubScreen(navController, starViewModel)
        }
    }
}
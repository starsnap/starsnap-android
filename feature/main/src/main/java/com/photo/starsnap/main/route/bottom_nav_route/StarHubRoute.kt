package com.photo.starsnap.main.route.bottom_nav_route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.photo.starsnap.main.ui.screen.main.star_hub.SearchHubScreen
import com.photo.starsnap.main.utils.NavigationRoute
import com.photo.starsnap.main.viewmodel.main.StarViewModel
import com.photo.starsnap.main.viewmodel.main.UploadViewModel

fun NavGraphBuilder.StarHubRoute(navController: NavController, starViewModel: StarViewModel) {
    navigation(
        startDestination = NavigationRoute.STAR_HUB,
        route = NavigationRoute.STAR_HUB_ROUTE
    ) {
        composable(NavigationRoute.STAR_HUB) {
            SearchHubScreen(navController, starViewModel)
        }
    }
}
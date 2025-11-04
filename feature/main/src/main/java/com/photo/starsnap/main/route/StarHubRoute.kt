package com.photo.starsnap.main.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.photo.starsnap.main.ui.screen.main.star_hub.StarGroupListScreen
import com.photo.starsnap.main.ui.screen.main.star_hub.StarGroupScreen
import com.photo.starsnap.main.ui.screen.main.star_hub.StarListScreen
import com.photo.starsnap.main.ui.screen.main.star_hub.StarScreen
import com.photo.starsnap.main.utils.NavigationRoute
import com.photo.starsnap.main.viewmodel.main.StarViewModel
import com.photo.starsnap.main.viewmodel.main.UploadViewModel

fun NavGraphBuilder.StarHubRoute(
    rootNavController: NavController,
    searchHubNavController: NavController,
    starViewModel: StarViewModel
) {
    navigation(
        startDestination = NavigationRoute.SEARCH_STAR,
        route = NavigationRoute.STAR_HUB_ROUTE
    ) {
        composable(NavigationRoute.SEARCH_STAR) {
            StarListScreen(rootNavController, searchHubNavController, starViewModel)
        }

        composable(NavigationRoute.SEARCH_STAR_GROUP) {
            StarGroupListScreen(rootNavController, searchHubNavController, starViewModel)
        }
    }
}
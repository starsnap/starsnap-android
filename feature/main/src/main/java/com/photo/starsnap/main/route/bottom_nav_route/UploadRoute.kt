package com.photo.starsnap.main.route.bottom_nav_route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.photo.starsnap.main.ui.screen.main.upload.PickImageScreen
import com.photo.starsnap.main.ui.screen.main.upload.PickStarScreen
import com.photo.starsnap.main.ui.screen.main.upload.SetSnapScreen
import com.photo.starsnap.main.ui.screen.main.upload.PickStarGroupScreen
import com.photo.starsnap.main.utils.NavigationRoute

fun NavGraphBuilder.UploadRoute(navController: NavController) {
    navigation(
        startDestination = NavigationRoute.PICK_IMAGE,
        route = NavigationRoute.UPLOAD_ROUTE
    ) {
        composable(NavigationRoute.PICK_IMAGE) {
            PickImageScreen(navController)
        }

        composable(NavigationRoute.SET_SNAP) {
            SetSnapScreen(navController)
        }

        composable(NavigationRoute.PICK_STAR) {
            PickStarScreen(navController)
        }
        composable(NavigationRoute.PICK_STAR_GROUP) {
            PickStarGroupScreen(navController)
        }
    }
}
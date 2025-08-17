package com.photo.starsnap.main.route.bottom_nav_route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.photo.starsnap.main.ui.screen.main.snap_list.SnapListScreen
import com.photo.starsnap.main.ui.screen.main.snap_list.SnapScreen
import com.photo.starsnap.main.utils.NavigationRoute
import com.photo.starsnap.main.viewmodel.main.SnapViewModel

fun NavGraphBuilder.HomeRoute(navController: NavController, snapViewModel: SnapViewModel) {
    navigation(startDestination = NavigationRoute.SNAP_LIST, route = NavigationRoute.HOME_ROUTE) {
        composable(NavigationRoute.SNAP_LIST) {
            SnapListScreen(navController, snapViewModel)
        }
        composable(NavigationRoute.SNAP) {
            SnapScreen(navController, snapViewModel)
        }
    }
}
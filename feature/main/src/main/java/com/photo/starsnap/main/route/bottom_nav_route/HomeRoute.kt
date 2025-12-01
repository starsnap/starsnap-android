package com.photo.starsnap.main.route.bottom_nav_route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.photo.starsnap.main.ui.screen.main.snap_list.SnapListScreen
import com.photo.starsnap.main.ui.screen.main.snap_list.SnapScreen
import com.photo.starsnap.main.utils.NavigationRoute
import com.photo.starsnap.main.viewmodel.main.SnapViewModel

@Composable
fun HomeRoute(navController: NavController, snapViewModel: SnapViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.SNAP_LIST,
        route = NavigationRoute.HOME_ROUTE
    ) {
        composable(NavigationRoute.SNAP_LIST) {
            SnapListScreen(navController, snapViewModel)
        }
        composable(NavigationRoute.SNAP) {
            SnapScreen(navController, snapViewModel)
        }
    }
}
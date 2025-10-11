package com.photo.starsnap.main.route.bottom_nav_route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.photo.starsnap.main.ui.screen.main.profile.ProfileScreen
import com.photo.starsnap.main.ui.screen.main.setting.SettingScreen
import com.photo.starsnap.main.utils.NavigationRoute
import com.photo.starsnap.main.viewmodel.main.UserViewModel

fun NavGraphBuilder.UserRoute(navController: NavController, userViewModel: UserViewModel) {
    navigation(startDestination = NavigationRoute.PROFILE, route = NavigationRoute.USER_ROUTE) {
        composable(NavigationRoute.PROFILE) {
            ProfileScreen(navController, userViewModel)
        }
        composable(NavigationRoute.SETTING_ROUTE) {
            SettingScreen(navController)
        }
    }
}
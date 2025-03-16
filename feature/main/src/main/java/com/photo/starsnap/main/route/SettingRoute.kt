package com.photo.starsnap.main.route

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.photo.starsnap.main.utils.NavigationRoute

fun NavGraphBuilder.SettingRoute() {
    navigation(
        startDestination = NavigationRoute.SETTING,
        route = NavigationRoute.SETTING_ROUTE,
    ) {
        composable(NavigationRoute.SETTING) { }
        composable(NavigationRoute.AUTH_SETTING) { }
        composable(NavigationRoute.REPORT_LIST) { }
        composable(NavigationRoute.BLOCK_LIST) { }
        composable(NavigationRoute.SAVE_LIST) { }
        composable(NavigationRoute.ALARM_SETTING) { }
        composable(NavigationRoute.FIX_PROFILE) { }
    }
}
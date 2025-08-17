package com.photo.starsnap.main.route.root_route

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.photo.starsnap.main.ui.screen.main.MainScreen
import com.photo.starsnap.main.utils.NavigationRoute.MAIN
import com.photo.starsnap.main.utils.NavigationRoute.MAIN_ROUTE

fun NavGraphBuilder.MainRoute() {
    navigation(startDestination = MAIN, route = MAIN_ROUTE) {
        composable(MAIN){
            MainScreen()
        }
    }
}
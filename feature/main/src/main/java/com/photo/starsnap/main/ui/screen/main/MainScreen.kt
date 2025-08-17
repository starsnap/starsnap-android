package com.photo.starsnap.main.ui.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.photo.starsnap.main.ui.component.BottomNavigation
import com.photo.starsnap.main.route.bottom_nav_route.HomeRoute
import com.photo.starsnap.main.route.bottom_nav_route.SearchRoute
import com.photo.starsnap.main.route.bottom_nav_route.StarHubRoute
import com.photo.starsnap.main.route.bottom_nav_route.UploadRoute
import com.photo.starsnap.main.route.bottom_nav_route.UserRoute
import com.photo.starsnap.main.utils.NavigationRoute.HOME_ROUTE
import com.photo.starsnap.main.viewmodel.main.SnapViewModel
import com.photo.starsnap.main.viewmodel.main.UploadViewModel

@Composable
fun MainScreen(
    snapViewModel: SnapViewModel = hiltViewModel(),
    uploadViewModel: UploadViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = HOME_ROUTE,
            modifier = Modifier.padding(padding)
        ) {
            HomeRoute(navController, snapViewModel)
            UserRoute(navController)
            UploadRoute(navController, snapViewModel, uploadViewModel)
            StarHubRoute(navController)
            SearchRoute(navController)
        }
    }

}
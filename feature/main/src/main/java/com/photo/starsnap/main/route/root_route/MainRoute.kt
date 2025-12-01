package com.photo.starsnap.main.route.root_route

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.photo.starsnap.main.route.bottom_nav_route.UploadRoute
import com.photo.starsnap.main.ui.screen.main.MainScreen
import com.photo.starsnap.main.ui.screen.main.profile.ProfileScreen
import com.photo.starsnap.main.ui.screen.main.profile.UserScreen
import com.photo.starsnap.main.ui.screen.main.setting.SettingScreen
import com.photo.starsnap.main.ui.screen.main.star_hub.StarGroupScreen
import com.photo.starsnap.main.ui.screen.main.star_hub.StarScreen
import com.photo.starsnap.main.utils.NavigationRoute.MAIN
import com.photo.starsnap.main.utils.NavigationRoute.MAIN_ROUTE
import com.photo.starsnap.main.viewmodel.main.SnapViewModel
import com.photo.starsnap.main.viewmodel.main.StarViewModel
import com.photo.starsnap.main.viewmodel.main.UploadViewModel
import com.photo.starsnap.main.viewmodel.main.UserViewModel

@Composable
fun MainRoute(
    rootNavController: NavHostController,
    uploadViewModel: UploadViewModel = hiltViewModel(),
    snapViewModel: SnapViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    starViewModel: StarViewModel = hiltViewModel()
) {
    val mainNavController = rememberNavController()
    NavHost(
        navController = mainNavController,
        startDestination = MAIN,
        route = MAIN_ROUTE
    ) {
        composable(MAIN) {
            MainScreen(
                rootNavController = rootNavController,
                mainNavController = mainNavController,
                uploadViewModel = uploadViewModel,
                snapViewModel = snapViewModel,
                userViewModel = userViewModel,
                starViewModel = starViewModel
            ){
                mainNavController.navigate(it)
            }
        }
        composable("star") {
            StarScreen(
                mainNavController,
                starViewModel,
                userViewModel
            )
        }
        composable("star_group") {
            StarGroupScreen(mainNavController)
        }
        composable("profile") {
            ProfileScreen(mainNavController, userViewModel)
        }
        composable("user") {
            UserScreen(mainNavController)
        }
        composable("setting") {
            SettingScreen(mainNavController)
        }
        composable("add_snap") {
            UploadRoute(mainNavController, snapViewModel, uploadViewModel)
        }
    }
}
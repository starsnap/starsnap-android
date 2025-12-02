package com.photo.starsnap.main.ui.screen.main

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.photo.starsnap.main.ui.component.BottomNavigation
import com.photo.starsnap.main.route.bottom_nav_route.HomeRoute
import com.photo.starsnap.main.route.bottom_nav_route.SearchRoute
import com.photo.starsnap.main.route.bottom_nav_route.StarHubRoute
import com.photo.starsnap.main.route.bottom_nav_route.UserRoute
import com.photo.starsnap.main.utils.BottomNavItem
import com.photo.starsnap.main.utils.NavigationRoute.HOME_ROUTE
import com.photo.starsnap.main.viewmodel.main.SnapViewModel
import com.photo.starsnap.main.viewmodel.main.StarViewModel
import com.photo.starsnap.main.viewmodel.main.UploadViewModel
import com.photo.starsnap.main.viewmodel.main.UserViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    rootNavController: NavHostController,
    mainNavController: NavHostController,
    uploadViewModel: UploadViewModel,
    snapViewModel: SnapViewModel,
    userViewModel: UserViewModel,
    starViewModel: StarViewModel,
    onNavigate: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        Log.d("화면", "MainScreen")
        userViewModel.getUserData()
    }

    val bottomNavItems = remember {
        listOf(
            BottomNavItem.Home,
            BottomNavItem.Star,
            BottomNavItem.AddSnap,
            BottomNavItem.Search,
            BottomNavItem.User
        )
    }


    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        Log.d("현재 화면", "MainScreen: $currentRoute")
    }
    Scaffold(
        bottomBar = {
            if(currentRoute?.startsWith("main/upload") != true) {
                BottomNavigation(navController, bottomNavItems, onNavigate = onNavigate)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = HOME_ROUTE,
            modifier = Modifier.padding(padding), enterTransition = {
                if (targetState.destination.route == "main/upload/pick_image") {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        tween(300)
                    )
                } else {
                    fadeIn(animationSpec = tween(500))
                }
            },
            exitTransition = {
                fadeOut(animationSpec = tween(500))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(500))
            },
            popExitTransition = {
                if (initialState.destination.route == "main/upload/pick_image") {
                    // Popping Upload → fade out as well
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        tween(300)
                    )
                } else {
                    fadeOut(animationSpec = tween(500))
                }
            }
        ) {
            composable(BottomNavItem.Home.route) {
                HomeRoute(navController, snapViewModel)
            }
            composable(BottomNavItem.User.route) {
                UserRoute(navController, userViewModel)
            }
            composable(BottomNavItem.Star.route) {
                StarHubRoute(navController, starViewModel)
            }
            composable(BottomNavItem.Search.route) {
                SearchRoute(navController)
            }
//            composable(BottomNavItem.AddSnap.route) {
////                LaunchedEffect(Unit) {
////                    onNavigate("add_snap")
////                }
////                UploadRoute(navController, snapViewModel, uploadViewModel)
//            }
        }
    }
}
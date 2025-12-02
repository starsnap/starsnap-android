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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.photo.starsnap.main.ui.component.BottomNavigation
import com.photo.starsnap.main.route.bottom_nav_route.HomeRoute
import com.photo.starsnap.main.route.bottom_nav_route.StarHubRoute
import com.photo.starsnap.main.ui.screen.main.profile.ProfileScreen
import com.photo.starsnap.main.ui.screen.main.search.SearchScreen
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
            BottomNavigation(navController, bottomNavItems, onNavigate = onNavigate)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = HOME_ROUTE,
            modifier = Modifier.padding(padding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeRoute(navController, snapViewModel)
            }
            composable(BottomNavItem.User.route) {
                ProfileScreen(navController, userViewModel)
            }
            composable(BottomNavItem.Star.route) {
                StarHubRoute(rootNavController, starViewModel, onNavigate)
            }
            composable(BottomNavItem.Search.route) {
                SearchScreen(navController)
            }
        }
    }
}
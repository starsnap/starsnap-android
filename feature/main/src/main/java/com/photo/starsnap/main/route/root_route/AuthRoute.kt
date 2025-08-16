package com.photo.starsnap.main.route.root_route

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.photo.starsnap.main.ui.screen.auth.LoginScreen
import com.photo.starsnap.main.ui.screen.auth.SignupScreen
import com.photo.starsnap.main.utils.NavigationRoute.AUTH_ROUTE
import com.photo.starsnap.main.utils.NavigationRoute.LOGIN
import com.photo.starsnap.main.utils.NavigationRoute.MAIN_ROUTE
import com.photo.starsnap.main.utils.NavigationRoute.SIGNUP

fun NavGraphBuilder.AuthRoute(navController: NavHostController) {
    navigation(startDestination = LOGIN, route = AUTH_ROUTE) {
        /*
        * https://tomas-repcik.medium.com/jetpack-compose-and-screen-transition-animations-b361fc8164cc
        * Transition 애니메이션 참고
        * */
        composable(
            LOGIN, // 처음 페이지
            // 열때 효과
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(400)
                )
            },
            // 닫을때 효과
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(400)
                )
            }
        ) {
            LoginScreen(moveSignupNavigation = {
                navController.navigate(SIGNUP)
            }, moveMainNavigation = {
                navController.navigate(MAIN_ROUTE) {
                    popUpTo(LOGIN) { inclusive = true }
                }
            })
        }
        composable(SIGNUP) {
            SignupScreen(onNavigateToLogin = {
                navController.navigate(LOGIN){
                    popUpTo(LOGIN) { inclusive = true }
                }
            })
        }
    }
}
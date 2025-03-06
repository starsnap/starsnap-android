package com.photo.starsnap.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.compose.rememberNavController
import com.photo.starsnap.main.ui.screen.auth.LoginScreen
import com.photo.starsnap.main.ui.screen.auth.SignupScreen
import com.photo.starsnap.main.utils.NavigationRoute.AUTH_ROUTE
import com.photo.starsnap.main.utils.NavigationRoute.LOGIN_ROUTE
import com.photo.starsnap.main.utils.NavigationRoute.MAIN_ROUTE
import com.photo.starsnap.main.utils.NavigationRoute.SIGNUP_ROUTE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashscreen = installSplashScreen()

        setContent {
//            val splashScreenState = remember { mutableStateOf(true) }
//            splashscreen.setKeepOnScreenCondition { splashScreenState.value }
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "AUTH") {
                navigation(startDestination = LOGIN_ROUTE, route = AUTH_ROUTE) {
                    /*
                    * https://tomas-repcik.medium.com/jetpack-compose-and-screen-transition-animations-b361fc8164cc
                    * Transition 애니메이션 참고
                    * */
                    composable(
                        LOGIN_ROUTE,
                        // 열때 효과
                        enterTransition = {
                            return@composable slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.End, tween(300)
                            )
                        },
                        // 닫을때 효과
                        exitTransition = {
                            return@composable slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Start, tween(300)
                            )
                        }
                    ) {
                        LoginScreen(moveSignupNavigation = {
                            navController.navigate(SIGNUP_ROUTE)
                        }, moveMainNavigation = {
                            navController.navigate(MAIN_ROUTE) {
                                popUpTo(LOGIN_ROUTE) { inclusive = true }
                            }
                        })
                    }
                    composable(SIGNUP_ROUTE) {
                        SignupScreen(onNavigateToLogin = {
                            navController.navigate(LOGIN_ROUTE){
                                popUpTo(0) { inclusive = true }
                            }
                        })
                    }
                }
            }
        }
    }
}
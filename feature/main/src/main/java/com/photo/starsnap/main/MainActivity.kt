package com.photo.starsnap.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.photo.starsnap.main.route.root_route.AuthRoute
import com.photo.starsnap.main.route.root_route.MainRoute
import com.photo.starsnap.main.utils.NavigationRoute
import com.photo.starsnap.main.viewmodel.auth.LoginViewModel
import com.photo.starsnap.main.viewmodel.state.AutoLoginState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashscreen = installSplashScreen()
        val loginViewModel: LoginViewModel by viewModels()
        setContent {
            val splashScreenState = remember { mutableStateOf(true) }
            var startDestination = NavigationRoute.AUTH_ROUTE
            loginViewModel.reissueToken()
            splashscreen.setKeepOnScreenCondition { splashScreenState.value }
            loginViewModel.autoLoginState.observe(this) {
                if(it == AutoLoginState.Success) {
                    startDestination = NavigationRoute.MAIN_ROUTE
                }
                splashScreenState.value = false
            }

            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = startDestination) {
                composable(NavigationRoute.AUTH_ROUTE) {
                    AuthRoute(navController)
                }
                composable(NavigationRoute.MAIN_ROUTE) {
                    MainRoute(navController)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
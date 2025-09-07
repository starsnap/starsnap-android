package com.photo.starsnap.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.photo.starsnap.main.route.root_route.AuthRoute
import com.photo.starsnap.main.route.root_route.MainRoute
import com.photo.starsnap.main.utils.NavigationRoute.AUTH_ROUTE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val splashscreen = installSplashScreen()

        setContent {
//            val splashScreenState = remember { mutableStateOf(true) }
//            splashscreen.setKeepOnScreenCondition { splashScreenState.value }

            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = AUTH_ROUTE) {
                AuthRoute(navController)
                MainRoute(navController)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
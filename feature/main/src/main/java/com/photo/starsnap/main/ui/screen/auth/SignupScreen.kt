package com.photo.starsnap.main.ui.screen.auth

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.photo.starsnap.main.ui.screen.auth.signup.ConsentSignupScreen
import com.photo.starsnap.main.ui.screen.auth.signup.EmailSignupScreen
import com.photo.starsnap.main.ui.screen.auth.signup.LoadingSignupScreen
import com.photo.starsnap.main.ui.screen.auth.signup.PasswordSignupScreen
import com.photo.starsnap.main.ui.screen.auth.signup.UserNameSignupScreen
import com.photo.starsnap.main.ui.screen.auth.signup.VerifySignupScreen
import com.photo.starsnap.main.utils.NavigationRoute.SIGNUP_CONSENT_ROUTE
import com.photo.starsnap.main.utils.NavigationRoute.SIGNUP_EMAIL_ROUTE
import com.photo.starsnap.main.utils.NavigationRoute.SIGNUP_LOADING_ROUTE
import com.photo.starsnap.main.utils.NavigationRoute.SIGNUP_PASSWORD_ROUTE
import com.photo.starsnap.main.utils.NavigationRoute.SIGNUP_ROUTE
import com.photo.starsnap.main.utils.NavigationRoute.SIGNUP_USERNAME_ROUTE
import com.photo.starsnap.main.utils.NavigationRoute.SIGNUP_VERIFY_ROUTE
import com.photo.starsnap.main.viewmodel.auth.SignupViewModel

@Composable
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "SIGNUP") {
        navigation(startDestination = SIGNUP_USERNAME_ROUTE, route = SIGNUP_ROUTE) {
            // 닉네임 입력 화면
            composable(SIGNUP_USERNAME_ROUTE) {
                UserNameSignupScreen(viewModel, navController, onNavigateToLogin)
            }
            // 비밀번호 입력 화면
            composable(SIGNUP_PASSWORD_ROUTE) {
                PasswordSignupScreen(viewModel, navController)
            }
            // 이메일 입력 화면
            composable(SIGNUP_EMAIL_ROUTE) {
                EmailSignupScreen(viewModel, navController)
            }
            // 이메일 인증 화면
            composable(SIGNUP_VERIFY_ROUTE) {
                VerifySignupScreen(viewModel, navController)
            }
            // 약관동의 화면
            composable(SIGNUP_CONSENT_ROUTE) {
                ConsentSignupScreen(viewModel, navController)
            }
            // 마지막 화면
            composable(SIGNUP_LOADING_ROUTE) {
                LoadingSignupScreen(viewModel, navController, onNavigateToLogin)
            }
        }
    }
}
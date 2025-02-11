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
import com.photo.starsnap.main.utils.NavigationRoute.SIGNUP_USERNAME_ROUTE
import com.photo.starsnap.main.utils.NavigationRoute.SIGNUP_VERIFY_ROUTE
import com.photo.starsnap.main.viewmodel.auth.SignupViewModel

@Composable
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "USERNAME") {
        navigation(startDestination = SIGNUP_USERNAME_ROUTE, route = "") {
            // 닉네임 입력 화면
            composable(SIGNUP_USERNAME_ROUTE) {
                UserNameSignupScreen(viewModel, navController)
            }
            // 비밀번호 입력 화면
            composable(SIGNUP_PASSWORD_ROUTE) {
                PasswordSignupScreen(viewModel, navController)
            }
            // 이메일 입력 화면
            composable(SIGNUP_EMAIL_ROUTE) {
                EmailSignupScreen(viewModel, navController)
            }
            composable(SIGNUP_VERIFY_ROUTE) {
                VerifySignupScreen(viewModel, navController)
            }
            // 약관동의 화면
            composable(SIGNUP_CONSENT_ROUTE) {
                ConsentSignupScreen(viewModel, navController)
            }
            // 마지막 화면
            composable(SIGNUP_LOADING_ROUTE) {
                LoadingSignupScreen(viewModel, navController)
            }
        }
    }
    /* Scaffold(topBar = { SignupAppBar(onNavigateToLogin) }) { innerPadding ->
       var loginButtonEnabled by remember { mutableStateOf(false) }
       var emailSendButtonEnabled by remember { mutableStateOf(false) }
       var codeVerifyButtonEnabled by remember { mutableStateOf(false) }

       Column(
           modifier = Modifier
               .fillMaxSize()
               .padding(top = innerPadding.calculateTopPadding(), start = 30.dp, end = 30.dp)
       ) {
           EditText(stringResource(R.string.signup_edit_text_username_hint)) { username = it }
           Spacer(Modifier.height(10.dp))
           PasswordEditText(stringResource(R.string.signup_edit_text_password_hint)) {
               password = it
           }
           Spacer(Modifier.height(10.dp))
           PasswordEditText(stringResource(R.string.signup_edit_text_verify_password_hint)) {
               password = it
           }
           Spacer(Modifier.height(10.dp))
           // 이에일 인증번호 전송
           Row(
               modifier = Modifier
                   .fillMaxWidth(),
               verticalAlignment = Alignment.CenterVertically
           ) {
               Box(Modifier.weight(1F)) {
                   EditText(stringResource(R.string.signup_edit_text_email_hint)) { email = it }
               }
               Spacer(Modifier.width(5.dp))
               SubmitButton(
                   buttonText = stringResource(R.string.signup_edit_text_email_hint),
                   enabled = emailSendButtonEnabled,
                   onClick = {})
           }
           Spacer(Modifier.height(10.dp))
           // 이에일 인증번호 확인
           Row(modifier = Modifier.fillMaxWidth()) {
               Box(Modifier.weight(1F)) {
                   NumberEditText(stringResource(R.string.signup_edit_text_code_hint)) {
                       code = it
                   }
               }
               Spacer(Modifier.width(5.dp))
               SubmitButton(
                   buttonText = stringResource(R.string.signup_verify_submit_button_text),
                   enabled = codeVerifyButtonEnabled,
                   onClick = {})
           }

           Spacer(Modifier.weight(1F))
           MainButton(
               { signupViewModel.signup(username, password, email, token) },
               loginButtonEnabled,
               stringResource(R.string.signup)
           )
           Spacer(Modifier.height(30.dp))
       }

   } */
}
package com.photo.starsnap.main.ui.screen.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.text.CustomTextStyle.SignupTitle
import com.photo.starsnap.main.ui.component.NextButton
import com.photo.starsnap.main.ui.component.SignupAppBar
import com.photo.starsnap.main.utils.NavigationRoute.AUTH_SIGNUP_LOADING_ROUTE
import com.photo.starsnap.main.viewmodel.auth.SignupViewModel
import com.photo.starsnap.main.viewmodel.auth.State

@Composable
fun ConsentSignupScreen(viewModel: SignupViewModel, navController: NavController) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.signupState) {
        if (uiState.signupState == State.LOADING) {
            navController.navigate(AUTH_SIGNUP_LOADING_ROUTE)
        }
    }

    Scaffold(
        topBar = { SignupAppBar { navController.popBackStack() } },
        bottomBar = {
            // 회원가입 버튼
            NextButton(
                event = {
                    viewModel.signup()
                },
                buttonText = "회원가입",
//                enabled = uiState.consentButtonState
                enabled = true
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .padding(innerPadding)
        ) {
            Text(
                stringResource(R.string.signup_consent_screen_title),
                style = SignupTitle,
                modifier = Modifier.padding(top = 30.dp)
            )
        }
    }
}
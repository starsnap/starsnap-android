package com.photo.starsnap.main.ui.screen.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.text.CustomTextStyle.SignupTitle
import com.photo.starsnap.main.ui.component.NextButton
import com.photo.starsnap.main.viewmodel.auth.SignupViewModel
import com.photo.starsnap.main.viewmodel.auth.State

@Composable
fun LoadingSignupScreen(
    viewModel: SignupViewModel, navController: NavController, onNavigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val loadingComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.signup_loading)
    )

    val successComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.signup_success)
    )

    val errorComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.signup_error)
    )

    val (title, composition) = when (uiState.signupState) {
        State.LOADING -> stringResource(R.string.signup_loading_screen_title) to loadingComposition
        State.SUCCESS -> stringResource(R.string.signup_success_screen_title) to successComposition
        State.ERROR -> stringResource(R.string.signup_error_screen_title) to errorComposition
        State.DEFAULT -> stringResource(R.string.signup_error_screen_title) to errorComposition
        State.INTERNET_ERROR -> stringResource(R.string.internet_error) to errorComposition
    }

    Scaffold(
        bottomBar = {
            if (uiState.signupState != State.DEFAULT && uiState.signupState != State.LOADING) {
                // 다음 버튼
                NextButton(
                    event = {
                        onNavigateToLogin()
                    },
                    buttonText = "나가기",
                    enabled = true
                )
            }
        }
    ) { innerPadding ->
        Column(
            Modifier.padding(horizontal = 35.dp).padding(innerPadding)
        ) {
            Spacer(Modifier.height(56.dp))
            Text(
                title,
                style = SignupTitle,
                modifier = Modifier.padding(top = 30.dp)
            )

            LottieAnimation(
                composition = composition,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun Loading() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.signup_error)
    )

    LottieAnimation(
        composition = composition,
        restartOnPlay = true,
        speed = 1.2f,
        iterations = LottieConstants.IterateForever
    )
}
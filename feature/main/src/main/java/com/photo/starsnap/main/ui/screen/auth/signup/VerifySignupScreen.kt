package com.photo.starsnap.main.ui.screen.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.text.CustomTextStyle.SignupTitle
import com.photo.starsnap.designsystem.text.CustomTextStyle.hint1
import com.photo.starsnap.main.ui.component.CheckVerifyCodeStatusMessage
import com.photo.starsnap.main.ui.component.NextButton
import com.photo.starsnap.main.ui.component.TopAppBar
import com.photo.starsnap.main.ui.component.TextButton
import com.photo.starsnap.main.ui.component.VerifyCodeEditText
import com.photo.starsnap.main.ui.component.VerifyCodeTimer
import com.photo.starsnap.main.utils.NavigationRoute.SIGNUP_CONSENT
import com.photo.starsnap.main.viewmodel.auth.SignupViewModel
import com.photo.starsnap.main.viewmodel.auth.VerifyCodeState

@Composable
fun VerifySignupScreen(viewModel: SignupViewModel, navController: NavController) {

    val uiState by viewModel.uiState.collectAsState()
    val timerUiState by viewModel.timerUiState.collectAsState()

    val resendButtonText by remember {
        derivedStateOf {
            if (timerUiState.resendTime == 0L) "인증번호 재전송" else "${timerUiState.resendTime}초 후 재전송 가능"
        }
    }

    LaunchedEffect(uiState.verifyCodeState) {
        // 코드 인증 성공시 약관동의 화면으로 이동
        if (uiState.verifyCodeState == VerifyCodeState.SUCCESS) {
            navController.navigate(SIGNUP_CONSENT)
            viewModel.resetVerifyCodeState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.signup_top_bar_title),
                navController = navController
            )
        },
        bottomBar = {
            // 다음 버튼
            NextButton(
                event = { viewModel.checkVerifyCode(viewModel.verifyCode) },
                buttonText = "인증하기",
                enabled = uiState.verifyButtonState
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
                .padding(innerPadding)
        ) {

            Text(
                stringResource(R.string.signup_verify_screen_title),
                style = SignupTitle,
                modifier = Modifier.padding(top = 30.dp, bottom = 12.dp)
            )

            CheckVerifyCodeStatusMessage(viewModel)

            Spacer(Modifier.height(55.dp))

            // 인증번호 입력 칸?
            VerifyCodeEditText(viewModel)

            Spacer(Modifier.height(24.dp))

            TextButton(
                resendButtonText,
                { viewModel.sendEmail() },
                hint1,
                Modifier.fillMaxWidth(),
                timerUiState.resendTime == 0L
            )

            Spacer(Modifier.height(31.dp))

            VerifyCodeTimer(timerUiState.timerValue)
        }
    }
}
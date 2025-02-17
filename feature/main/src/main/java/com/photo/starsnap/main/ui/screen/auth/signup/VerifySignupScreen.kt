package com.photo.starsnap.main.ui.screen.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.text.CustomTextStyle.SignupTitle
import com.photo.starsnap.main.ui.component.NextButton
import com.photo.starsnap.main.ui.component.SignupAppBar
import com.photo.starsnap.main.ui.component.VerifyCodeEditText
import com.photo.starsnap.main.utils.NavigationRoute.SIGNUP_CONSENT_ROUTE
import com.photo.starsnap.main.viewmodel.auth.SignupViewModel

@Composable
fun VerifySignupScreen(viewModel: SignupViewModel, navController: NavController) {
    Scaffold(topBar = { SignupAppBar { navController.popBackStack() } }) { innerPadding ->
    val timerUiState by viewModel.timerUiState.collectAsState()
        Column(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .padding(innerPadding)
        ) {

            val uiState by viewModel.uiState.collectAsState()
            var verifyCode by remember { mutableStateOf("") }

            Text(
                stringResource(R.string.signup_verify_screen_title),
                style = SignupTitle,
                modifier = Modifier.padding(top = 30.dp)
            )

            Spacer(Modifier.height(55.dp))

            VerifyCodeEditText { verifyCode = it }

            Spacer(Modifier.weight(1F))
            // 다음 버튼
            NextButton(
                event = { navController.navigate(SIGNUP_CONSENT_ROUTE) },
                buttonText = "다음",
                enabled = uiState.verifyButtonState
            )

            VerifyCodeTimer(timerUiState.timerValue)
        }
    }
}
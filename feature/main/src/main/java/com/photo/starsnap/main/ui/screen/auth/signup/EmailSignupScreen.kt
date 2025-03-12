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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.text.CustomTextStyle.SignupTitle
import com.photo.starsnap.main.ui.component.BaseEditText
import com.photo.starsnap.main.ui.component.CheckEmailStatusMessage
import com.photo.starsnap.main.ui.component.NextButton
import com.photo.starsnap.main.ui.component.SignupAppBar
import com.photo.starsnap.main.utils.EditTextType
import com.photo.starsnap.main.utils.NavigationRoute.AUTH_SIGNUP_VERIFY_ROUTE
import com.photo.starsnap.main.viewmodel.auth.SignupViewModel

@Composable
fun EmailSignupScreen(viewModel: SignupViewModel, navController: NavController) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { SignupAppBar { navController.popBackStack() } },
        bottomBar = {
            // 다음 버튼
            NextButton(
                event = { navController.navigate(AUTH_SIGNUP_VERIFY_ROUTE)
                    viewModel.sendEmail()
                        },
                buttonText = "인증번호 전송",
                enabled = uiState.emailSendButtonState
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .padding(innerPadding)
        ) {

            Text(
                stringResource(R.string.signup_email_screen_title),
                style = SignupTitle,
                modifier = Modifier.padding(top = 30.dp, bottom = 13.dp)
            )

            CheckEmailStatusMessage(viewModel)

            Spacer(Modifier.height(55.dp))

            BaseEditText(
                viewModel.email,
                "이메일",
                { viewModel.email = it },
                editTextType = EditTextType.Email
            )

            Spacer(Modifier.weight(1F))
        }
    }
}
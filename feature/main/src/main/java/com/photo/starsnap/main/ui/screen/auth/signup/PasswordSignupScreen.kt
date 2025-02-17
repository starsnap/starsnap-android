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
import com.photo.starsnap.main.ui.component.CheckPasswordStatusMessage
import com.photo.starsnap.main.ui.component.NextButton
import com.photo.starsnap.main.ui.component.SignupAppBar
import com.photo.starsnap.main.utils.EditTextType
import com.photo.starsnap.main.utils.NavigationRoute.SIGNUP_EMAIL_ROUTE
import com.photo.starsnap.main.viewmodel.auth.SignupViewModel

@Composable
fun PasswordSignupScreen(viewModel: SignupViewModel, navController: NavController) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { SignupAppBar { navController.popBackStack() } },
        bottomBar = {
            // 다음 버튼
            NextButton(
                event = { navController.navigate(SIGNUP_EMAIL_ROUTE) },
                buttonText = "다음",
                enabled = uiState.passwordButtonState
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .padding(innerPadding)
        ) {

            Text(
                stringResource(R.string.signup_password_screen_title),
                style = SignupTitle,
                modifier = Modifier.padding(top = 30.dp, bottom = 13.dp)
            )

            CheckPasswordStatusMessage(viewModel)

            Spacer(Modifier.height(55.dp))

            BaseEditText(
                defaultText = viewModel.password,
                hint = "비밀번호",
                inputText = { viewModel.password = it },
                editTextType = EditTextType.Password
            )
            Spacer(Modifier.height(10.dp))
            BaseEditText(
                defaultText = viewModel.confirmPassword,
                hint = "비밀번호 확인",
                inputText = { viewModel.confirmPassword = it },
                editTextType = EditTextType.Password
            )
        }
    }
}
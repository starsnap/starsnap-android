package com.photo.starsnap.main.screen.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.main.ui.component.EditText
import com.photo.starsnap.main.ui.component.SignupAppBar
import com.photo.starsnap.main.ui.component.MainButton
import com.photo.starsnap.main.ui.component.PasswordEditText
import com.photo.starsnap.main.ui.component.SubmitButton
import com.photo.starsnap.main.viewmodel.auth.SignupViewModel

@Composable
fun SignupScreen(
    signupViewModel: SignupViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var token by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    Scaffold(topBar = { SignupAppBar(onNavigateToLogin) }) { innerPadding ->
        var loginButtonEnabled by remember { mutableStateOf(false) }
        var emailSendButtonEnabled by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), start = 30.dp, end = 30.dp)
        ) {

            EditText(stringResource(R.string.signup_edit_text_username_hint)) { username = it }
            Spacer(Modifier.height(10.dp))
            PasswordEditText { password = it }
            Spacer(Modifier.height(10.dp))
            Row {
                EditText(stringResource(R.string.signup_edit_text_email_hint)) { email = it }
                SubmitButton(
                    buttonText = stringResource(R.string.signup_edit_text_email_hint),
                    enabled = emailSendButtonEnabled,
                    onClick = {})
            }
            Spacer(Modifier.height(10.dp))
            EditText(stringResource(R.string.signup_edit_text_code_hint)) { code = it }

            Spacer(Modifier.weight(1F))
            MainButton(
                { signupViewModel.signup(username, password, email, token) },
                loginButtonEnabled,
                stringResource(R.string.signup)
            )
            Spacer(Modifier.height(30.dp))
        }

    }
}
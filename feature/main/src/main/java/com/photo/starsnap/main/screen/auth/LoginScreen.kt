package com.photo.starsnap.main.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.main.ui.component.AppIcon
import com.photo.starsnap.main.ui.component.AppleLoginButton
import com.photo.starsnap.main.ui.component.EditText
import com.photo.starsnap.main.ui.component.GoogleLoginButton
import com.photo.starsnap.main.ui.component.LoginButton
import com.photo.starsnap.main.ui.component.PasswordEditText
import com.photo.starsnap.main.ui.component.TextButton
import com.photo.starsnap.main.viewmodel.auth.LoginViewModel

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = hiltViewModel()) {
    var isClickable by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    isClickable = username.isNotBlank() && password.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
    ) {
        AppIcon(
            Modifier
                .padding(vertical = 70.dp)
                .align(Alignment.CenterHorizontally)
        )
        EditText(stringResource(R.string.id)) { username = it }
        Spacer(Modifier.height(15.dp))
        PasswordEditText { password = it }
        Spacer(Modifier.height(20.dp))
        LoginButton({ loginViewModel.login(username, password) }, isClickable)
        Spacer(Modifier.height(24.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            TextButton(stringResource(R.string.find_username)) { }
            Spacer(Modifier.width(30.dp))
            TextButton(stringResource(R.string.find_password)) { }
            Spacer(Modifier.width(30.dp))
            TextButton(stringResource(R.string.signup)) { }
        }
        Spacer(Modifier.weight(1F))
        Column {
            GoogleLoginButton()
            Spacer(Modifier.height(10.dp))
            AppleLoginButton()
            Spacer(Modifier.height(30.dp))
        }
    }
}
package com.photo.starsnap.main.screen.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.photo.starsnap.main.viewmodel.auth.LoginViewModel

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = hiltViewModel()){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}
package com.photo.starsnap.main.ui.screen.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.photo.starsnap.main.ui.component.NextButton
import com.photo.starsnap.main.ui.component.SignupAppBar
import com.photo.starsnap.main.viewmodel.auth.SignupViewModel

@Composable
fun UserNameSignupScreen(viewModel: SignupViewModel, navController: NavController) {
    Scaffold(topBar = { SignupAppBar { navController.popBackStack() } }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .padding(innerPadding)
        ) {

            val uiState by viewModel.uiState.collectAsState()

            Text(
                stringResource(R.string.signup_username_screen_title),
                style = SignupTitle,
                modifier = Modifier.padding(top = 30.dp)
            )


            Spacer(Modifier.weight(1F))
            // 다음 버튼
            NextButton(
                event = { navController.navigate("PASSWORD") },
                buttonText = "다음",
                enabled = uiState.usernameButtonState
            )
        }
    }
}



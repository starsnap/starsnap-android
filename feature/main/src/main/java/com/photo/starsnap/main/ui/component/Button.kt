package com.photo.starsnap.main.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.yellow_400
import com.photo.starsnap.designsystem.yellow_100

@Composable
fun LoginButton(login: () -> Unit, isClickable: Boolean) {
    val buttonBackground = if (isClickable) yellow_400 else yellow_100
    Box(
        Modifier
            .clickable {
                if (isClickable) {
                    login()
                }
            }
            .height(45.dp)
            .fillMaxWidth()
            .background(buttonBackground, RoundedCornerShape(size = 8.dp))
    ) {
        Text(stringResource(R.string.login), Modifier.align(Alignment.Center))
    }
}

@Composable
fun TextButton(text: String, onClick: () -> Unit) {
    Box(Modifier.clickable(onClick = onClick)) {
        Text(text)
    }
}

@Composable
fun AppleLoginButton() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
    ) {
        Image(painterResource(R.drawable.apple_icon), "", Modifier.padding(start = 25.dp).align(Alignment.CenterStart))
        Text(stringResource(R.string.apple_login_button_text), Modifier.align(Alignment.Center), Color.White)
    }
}

@Composable
fun GoogleLoginButton() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(width = 1.dp, shape = RoundedCornerShape(12.dp), color = Color.Black)
    ) {
        Image(painterResource(R.drawable.google_icon), "", Modifier.padding(start = 25.dp).align(Alignment.CenterStart))
        Text(stringResource(R.string.google_login_button_text), Modifier.align(Alignment.Center))
    }
}
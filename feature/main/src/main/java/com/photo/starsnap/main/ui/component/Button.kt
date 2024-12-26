package com.photo.starsnap.main.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.CustomColor.yellow_400
import com.photo.starsnap.designsystem.CustomColor.yellow_100
import com.photo.starsnap.designsystem.text.CustomTextStyle.TitleMedium

// 로그인, 회원가입 등 메인 버튼으로 사용
@Composable
fun MainButton(event: () -> Unit, enabled: Boolean, buttonText: String) {
    val buttonBackground = if (enabled) yellow_400 else yellow_100
    Box(
        Modifier
            .clickable(onClick = {
                if (enabled) {
                    event()
                }
            }, interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .height(45.dp)
            .fillMaxWidth()
            .background(buttonBackground, RoundedCornerShape(size = 8.dp))
    ) {
        Text(buttonText, Modifier.align(Alignment.Center), style = TitleMedium)
    }
}

// 텍스트 버튼
@Composable
fun TextButton(text: String, onClick: () -> Unit, textStyle: TextStyle) {
    Box(
        Modifier.clickable(
            onClick = onClick,
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        )
    ) {
        Text(text, style = textStyle)
    }
}

// 애플 로그인 버튼(안드로이드에서는 사용할 일이 없음)
@Composable
fun AppleLoginButton(onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
    ) {
        Image(
            painterResource(R.drawable.apple_icon),
            "",
            Modifier
                .padding(start = 25.dp)
                .align(Alignment.CenterStart)
        )
        Text(
            stringResource(R.string.apple_login_button_text),
            Modifier.align(Alignment.Center),
            Color.White
        )
    }
}

// 구글 로그인 버튼
@Composable
fun GoogleLoginButton(onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(width = 1.dp, shape = RoundedCornerShape(12.dp), color = Color.Black)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
    ) {
        Image(
            painterResource(R.drawable.google_icon),
            "",
            Modifier
                .padding(start = 25.dp)
                .align(Alignment.CenterStart)
        )
        Text(stringResource(R.string.google_login_button_text), Modifier.align(Alignment.Center))
    }
}

@Composable
fun SubmitButton(onClick: () -> Unit, buttonText: String, enabled: Boolean) {
    val buttonBackground = if (enabled) yellow_400 else yellow_100

    Box(
        Modifier
            .height(50.dp)
            .background(buttonBackground, RoundedCornerShape(size = 8.dp))
            .padding(horizontal = 10.dp)
            .clickable(
                onClick = { if (enabled) onClick() },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
    ) {
        Text(buttonText, Modifier.align(Alignment.Center))
    }
}
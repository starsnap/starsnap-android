package com.photo.starsnap.main.ui.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.text.CustomTextStyle.TopBarTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupAppBar(back: () -> Unit) {
    CenterAlignedTopAppBar(
        expandedHeight = 56.dp,
        title = {
            Text(
                text = stringResource(R.string.signup_top_bar_title),
                maxLines = 1,
                style = TopBarTitle,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = back) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.back_arrow),
                    contentDescription = "Localized description"
                )
            }
        },
    )
}
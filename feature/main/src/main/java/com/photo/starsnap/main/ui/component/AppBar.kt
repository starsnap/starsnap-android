package com.photo.starsnap.main.ui.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.photo.starsnap.designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(R.string.signup_top_bar_title), maxLines = 1
            )
        },
    )
}
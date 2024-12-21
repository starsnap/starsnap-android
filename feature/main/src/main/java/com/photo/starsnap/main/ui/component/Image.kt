package com.photo.starsnap.main.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.photo.starsnap.designsystem.R

@Composable
fun AppIcon(modifier: Modifier) {
    Image(
        modifier = modifier.size(100.dp),
        painter = painterResource(id = R.drawable.app_icon),
        contentDescription = stringResource(id = R.string.app_icon)
    )
}
package com.photo.starsnap.main.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.photo.starsnap.designsystem.R

@Composable
fun CustomCheckBox(
    onPainter: Painter,
    offPainter: Painter,
    onCheckedChange: (Boolean) -> Unit,
) {
    var checkState by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.clickable(onClick = {
            checkState = !checkState
            onCheckedChange(checkState)
        }, interactionSource = remember {
            MutableInteractionSource()
        }, indication = null
        )

    ) {
        if (checkState) {
            Icon(
                painter = onPainter,
                contentDescription = "on",
            )
        } else {
            Icon(
                painter = offPainter,
                contentDescription = "off",
            )
        }
    }
}


@Composable
fun PasswordEyeCheckBox(checkState: (Boolean) -> Unit) {
    CustomCheckBox(
        onPainter = painterResource(R.drawable.eye_open),
        offPainter = painterResource(R.drawable.eye_close),
        onCheckedChange = checkState
    )
}
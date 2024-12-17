package com.photo.starsnap.main.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun CustomCheckBox(
    checked: Boolean,
    onPainter: Painter,
    offPainter: Painter,
    onCheckedChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier.clickable(onClick = {
            onCheckedChange(!checked)
        }, interactionSource = remember {
            MutableInteractionSource()
        }, indication = null
        )

    ) {
        if (checked) {
            Icon(
                painter = onPainter,
                contentDescription = "on",
            )
        }
        if (!checked) {
            Icon(
                painter = offPainter,
                contentDescription = "off",
            )
        }
    }
}
package com.photo.starsnap.main.ui.component

import android.util.Log
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.text.CustomTextStyle.TopBarTitle
import com.photo.starsnap.designsystem.text.CustomTextStyle.title2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(title: String, onBack: () -> Unit) {
    CenterAlignedTopAppBar(
        windowInsets = TopAppBarDefaults.windowInsets,
        title = {
            Text(
                text = title,
                maxLines = 1,
                style = TopBarTitle,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                Log.e("TopAppBarBack", "click_back:title=$title")
                onBack()
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.back_arrow_icon),
                    contentDescription = "Localized description"
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickImageTopAppBar(
    onClose: () -> Unit,
    onNext: () -> Unit,
) {
    CenterAlignedTopAppBar(
        windowInsets = TopAppBarDefaults.windowInsets,
        title = {
            Text(
                text = "새로운 스냅",
                maxLines = 1,
                style = TopBarTitle,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.close_icon),
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = onNext) {
                Text(text = "다음", style = title2)
            }
        }
    )
}

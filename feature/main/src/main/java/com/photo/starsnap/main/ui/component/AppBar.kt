package com.photo.starsnap.main.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.text.CustomTextStyle.TopBarTitle
import com.photo.starsnap.designsystem.text.CustomTextStyle.title2
import com.photo.starsnap.main.utils.clickableSingle
import com.photo.starsnap.main.utils.NavigationRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(title: String, navController: NavController) {
    CenterAlignedTopAppBar(
        windowInsets = WindowInsets(0),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = CustomColor.container,      // 배경색
            titleContentColor = CustomColor.title,             // 타이틀 텍스트 색
            navigationIconContentColor = CustomColor.title,    // 네비게이션 아이콘 색
            actionIconContentColor = CustomColor.title      // 액션 아이콘 색
        ),
        title = {
            Text(
                text = title,
                maxLines = 1,
                style = TopBarTitle,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .clickableSingle(onClick = { navController.popBackStack() })
                    .padding(start = 20.dp),
            ) {
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
fun PickImageTopAppBar(navController: NavController, isNextEnabled: Boolean) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = CustomColor.container,      // 배경색
            titleContentColor = CustomColor.title,             // 타이틀 텍스트 색
            navigationIconContentColor = CustomColor.title,    // 네비게이션 아이콘 색
            actionIconContentColor = CustomColor.title      // 액션 아이콘 색
        ),
        windowInsets = WindowInsets(0),
        title = {
            Text(
                text = "새로운 스냅",
                maxLines = 1,
                style = TopBarTitle,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .clickableSingle(onClick = { navController.popBackStack() })
                    .padding(start = 20.dp),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.close_icon),
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            Box(
                modifier = Modifier
                    .clickableSingle(onClick = {
                        if (isNextEnabled) navController.navigate(NavigationRoute.SET_SNAP)
                    })
                    .padding(end = 20.dp),
            ) {
                Text(text = "다음", style = title2)
            }
        }
    )
}

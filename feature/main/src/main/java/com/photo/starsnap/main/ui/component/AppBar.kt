package com.photo.starsnap.main.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.text.CustomTextStyle.TopBarTitle
import com.photo.starsnap.main.utils.clickableSingle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(title: String, navController: NavController) {
    CenterAlignedTopAppBar(
        expandedHeight = 56.dp,
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
                    imageVector = ImageVector.vectorResource(R.drawable.back_arrow),
                    contentDescription = "Localized description"
                )
            }
        },
    )
}
package com.photo.starsnap.main.ui.screen.main.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.main.ui.component.TopAppBar

@Composable
fun ReportListScreen(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(
            title = stringResource(R.string.report_list_top_app_bar_title),
            navController = navController
        )
    }) { padding ->
        Column(Modifier.padding(padding)) {

        }
    }
}
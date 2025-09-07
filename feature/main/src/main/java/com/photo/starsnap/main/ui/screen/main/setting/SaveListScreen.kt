package com.photo.starsnap.main.ui.screen.main.setting

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.main.ui.component.TopAppBar

@Composable
fun SaveListScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        Log.d("화면", "SaveListScreen")
    }
    Scaffold(topBar = {
        TopAppBar(
            title = stringResource(R.string.save_list_top_app_bar_title),
            navController = navController
        )
    }) { padding ->
        Column(Modifier.padding(padding)) {

        }
    }
}
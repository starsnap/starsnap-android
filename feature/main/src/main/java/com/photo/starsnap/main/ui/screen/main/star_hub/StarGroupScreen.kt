package com.photo.starsnap.main.ui.screen.main.star_hub

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController

@Composable
fun StarGroupScreen(
    mainNavController: NavController,
) {
    LaunchedEffect(Unit) {
        Log.d("화면", "StarGroupScreen")
    }
}
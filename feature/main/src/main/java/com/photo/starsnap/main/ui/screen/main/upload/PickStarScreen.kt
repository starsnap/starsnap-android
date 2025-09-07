package com.photo.starsnap.main.ui.screen.main.upload

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.photo.starsnap.main.viewmodel.main.UploadViewModel

@Composable
fun PickStarScreen(navController: NavController, uploadViewModel: UploadViewModel){
    LaunchedEffect(Unit) {
        Log.d("화면", "PickStarScreen")
    }
}
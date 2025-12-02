package com.photo.starsnap.main.ui.screen.main.star_hub

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.photo.starsnap.main.viewmodel.main.StarViewModel
import com.photo.starsnap.main.viewmodel.main.UserViewModel

@Composable
fun StarGroupScreen(
    mainNavController: NavController,
    starViewModel: StarViewModel,
    userViewModel: UserViewModel
) {
    val starGroup = starViewModel.selectedStarGroup.collectAsState().value

    LaunchedEffect(Unit) {
        Log.d("화면", "StarGroupScreen")
    }
}
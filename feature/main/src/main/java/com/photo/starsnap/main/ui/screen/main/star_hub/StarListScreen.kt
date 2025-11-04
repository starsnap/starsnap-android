package com.photo.starsnap.main.ui.screen.main.star_hub

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.photo.starsnap.main.viewmodel.main.StarViewModel
import com.photo.starsnap.main.viewmodel.main.UploadViewModel

@Composable
fun StarListScreen(
    rootNavController: NavController,
    searchHubNavController: NavController,
    starViewModel: StarViewModel
) {
    val starList = starViewModel.starList.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        Log.d("화면", "StarListScreen")
    }
}
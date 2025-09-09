package com.photo.starsnap.main.ui.screen.main.upload

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.main.ui.component.TopAppBar
import com.photo.starsnap.main.viewmodel.main.UploadViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun SetSnapScreen(navController: NavController, uploadViewModel: UploadViewModel) {
    LaunchedEffect(Unit) {
        Log.d("화면", "SetSnapScreen")
    }
    val selectedPhotos by uploadViewModel.selectedPhotos.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { selectedPhotos.size })

    Scaffold(
        topBar = { TopAppBar("새로운 스냅", navController) },
        containerColor = CustomColor.container,
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
            ) { page ->
                val photo = selectedPhotos[page]
                GlideImage(
                    modifier = Modifier
                        .animateContentSize()
                        .fillMaxSize(),
                    imageModel = { photo.imageUri },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.FillHeight,
                        alignment = Alignment.Center,
                        alpha = 1f
                    )
                )
            }
        }
    }
}
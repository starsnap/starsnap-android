package com.photo.starsnap.main.ui.screen.main.upload

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

    Scaffold(
        topBar = { TopAppBar("새로운 스냅", navController) },
        containerColor = CustomColor.container,
    ) { padding ->
        LazyHorizontalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            rows = GridCells.Fixed(1)
        ) {
            items(selectedPhotos.size) { index ->
                val image = selectedPhotos[index]
                GlideImage(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                        },
                    imageModel = { image.imageUri },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        alpha = 1f
                    )
                )
            }
        }
    }
}
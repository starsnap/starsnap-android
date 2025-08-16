package com.photo.starsnap.main.ui.screen.main.upload

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.photo.starsnap.main.ui.component.TopAppBar
import com.photo.starsnap.main.viewmodel.main.UploadViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun SetSnapScreen(navController: NavController, uploadViewModel: UploadViewModel) {
    val selectImage = uploadViewModel.selectedImage
    Scaffold(
        topBar = { TopAppBar("새로운 스냅", navController) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            GlideImage(
                modifier = Modifier.fillMaxWidth().height(400.dp),
                imageModel = { selectImage?.imageUri },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    alpha = 1f
                )
            )
        }
    }
}
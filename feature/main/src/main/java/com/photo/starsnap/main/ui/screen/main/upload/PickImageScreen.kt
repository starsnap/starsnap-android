package com.photo.starsnap.main.ui.screen.main.upload

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.main.ui.component.SelectImage
import com.photo.starsnap.main.ui.component.PickImageTopAppBar
import com.photo.starsnap.main.viewmodel.main.UploadViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PickImageScreen(
    navController: NavController,
    uploadViewModel: UploadViewModel
) {
    val pagingItems = uploadViewModel.customGalleryPhotoList.collectAsLazyPagingItems()
    val selectImages = uploadViewModel.selectedImage

    Scaffold(
        topBar = { PickImageTopAppBar(navController) },
        containerColor = CustomColor.container
    ) { padding ->
        LazyVerticalGrid(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
                .fillMaxHeight(),
            columns = GridCells.Fixed(3),
        ) {
            items(pagingItems.itemCount) { index ->
                pagingItems[index]?.let { galleryImage ->
                    Box {
                        val isSelected = selectImages?.id == galleryImage.id
                        GlideImage(
                            modifier = Modifier
                                .padding(1.dp)
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .aspectRatio(1f)
                                .animateContentSize()
                                .clickable {
                                    uploadViewModel.addSelectedImage(
                                        id = galleryImage.id, imageUri = galleryImage.uri
                                    )
                                },
                            imageModel = { galleryImage.uri },
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center,
                                alpha = 1f
                            )
                        )

                        if (isSelected) {
                            SelectImage()
                        }
                    }
                }
            }
        }
    }


}
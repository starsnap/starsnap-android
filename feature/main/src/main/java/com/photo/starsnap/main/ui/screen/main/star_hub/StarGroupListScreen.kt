package com.photo.starsnap.main.ui.screen.main.star_hub

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.main.viewmodel.main.StarViewModel
import com.photo.starsnap.main.viewmodel.main.UploadViewModel
import com.photo.starsnap.network.star.dto.StarGroupResponseDto
import com.photo.starsnap.network.star.dto.StarResponseDto
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun StarGroupListScreen(
    rootNavController: NavController,
    searchHubNavController: NavController,
    starViewModel: StarViewModel
) {
    val starGroupList = starViewModel.starGroupList.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        Log.d("화면", "StarGroupListScreen")
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(starGroupList.itemCount) { index ->
            val starGroup = starGroupList[index]
            if (starGroup != null) {
                StarGroupItem(starGroup)
            }
        }
    }
}

@Composable
fun StarGroupItem(starGroup: StarGroupResponseDto) {
    Box(
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
    ) {
        GlideImage(
            imageModel = { },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = CustomColor.light_gray)
        )
        Text(
            text = starGroup.name,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, bottom = 20.dp),
        )
    }
}
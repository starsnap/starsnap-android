package com.photo.starsnap.main.ui.screen.main.upload

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.main.utils.clickableSingle
import com.photo.starsnap.main.viewmodel.main.UploadViewModel
import com.photo.starsnap.network.star.dto.StarGroupResponseDto
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PickStarGroupScreen(navController: NavController, uploadViewModel: UploadViewModel) {
    var starGroup by remember { mutableStateOf("") }
    var selectStarGroup by remember { mutableStateOf(listOf("")) }
    val starGroupList = uploadViewModel.starGroupList.collectAsLazyPagingItems()
    val gridState = rememberLazyGridState()
    Scaffold(
        Modifier.padding(horizontal = 22.dp)
    ) { padding ->
        Column(Modifier.padding()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = starGroup,
                    onValueChange = { starGroup = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text(text = "StarGroup 검색") })
                Spacer(Modifier.width(20.dp))
                Box(
                    Modifier
                        .padding(end = 10.dp)
                        .align(alignment = Alignment.CenterVertically)
                        .clickableSingle {
                            navController.popBackStack()
                            uploadViewModel.setStarGroup(selectStarGroup)
                        }) {
                    Text("확인")
                }
            }
            LazyVerticalGrid(
                state = gridState,
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(1),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(starGroupList.itemCount) { index ->
                    val starGroup = starGroupList[index]
                    Log.d("StarGroupList", "index: $index, image: $starGroup")

                    if(starGroup != null){
                        Log.d("PickStarScreen", "추가")
                        StarGroupItem(starGroup){}
                    } else {
                        Box(Modifier.fillMaxWidth()
                            .height(170.dp).background(CustomColor.yellow_100))
                    }
                }
            }
        }
    }
}

@Composable
fun StarGroupItem(starGroup: StarGroupResponseDto, onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(170.dp)
            .clickableSingle { onClick() },
        contentAlignment = Alignment.CenterStart
    ) {
        GlideImage(
            modifier = Modifier.fillMaxSize()
                .clip(RoundedCornerShape(12.dp)),
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            imageModel = { starGroup.imageKey },
        )
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            Text(starGroup.name)
            Spacer(Modifier.height(2.dp))
            Text(starGroup.explanation.toString())
        }
    }
}
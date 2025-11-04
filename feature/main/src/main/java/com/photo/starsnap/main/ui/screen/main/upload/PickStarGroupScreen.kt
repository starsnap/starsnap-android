package com.photo.starsnap.main.ui.screen.main.upload

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.main.utils.clickableSingle
import com.photo.starsnap.main.viewmodel.main.UploadViewModel
import com.photo.starsnap.network.star.dto.StarGroupResponseDto
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay

@Composable
fun PickStarGroupScreen(navController: NavController, uploadViewModel: UploadViewModel) {
    var searchStarGroupName by remember { mutableStateOf("") }
    var selectStarGroups =
        uploadViewModel.selectedStarGroups.collectAsStateWithLifecycle() // 선택된 star-group
    val starGroupList = uploadViewModel.starGroupList(searchStarGroupName).collectAsLazyPagingItems() // 가져온 star-group
    val gridState = rememberLazyGridState() // grid 상태

    LaunchedEffect(searchStarGroupName) {
        delay(2000)
        starGroupList.refresh()
    }

    Scaffold(
        Modifier
            .padding(horizontal = 22.dp)
            .fillMaxSize(),
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .weight(1F)
                        .height(50.dp)
                ) {
                    SearchTextField("StarGroup 검색") {
                        searchStarGroupName = it
                    }
                }
                Box(
                    Modifier
                        .size(height = 70.dp, width = 60.dp)
                        .clickableSingle {
                            navController.popBackStack()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "확인"
                    )
                }
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            LazyVerticalGrid(
                state = gridState,
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(1),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(starGroupList.itemCount) { index ->
                    val starGroup = starGroupList[index]
                    Log.d("StarGroupList", "index: $index, image: $starGroup")

                    if (starGroup != null) {
                        Log.d("PickStarScreen", "추가")
                        val isSelected = selectStarGroups.value.any { it.id == starGroup.id }  // ← 선택 여부 계산

                        StarGroupItem(starGroup, isSelected) {
                            uploadViewModel.selectedStarGroup(starGroup)
                        }
                    } else {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(170.dp)
                                .background(CustomColor.yellow_100)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StarGroupItem(starGroup: StarGroupResponseDto, selected: Boolean, onClick: () -> Unit) {
    var onClickState by remember { mutableStateOf(selected) }
    Box(
        Modifier
            .fillMaxWidth()
            .background(color = CustomColor.container, shape = RoundedCornerShape(12.dp))
            .height(170.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
                onClickState = !onClickState
            }, contentAlignment = Alignment.CenterStart
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp)),
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop, alignment = Alignment.Center
            ),
            imageModel = { starGroup.imageKey },
        )
        Column(
            verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.Start
        ) {
            Text(starGroup.name)
            Spacer(Modifier.height(2.dp))
            Text(starGroup.explanation.toString())
        }
    }
}
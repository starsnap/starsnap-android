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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.main.utils.clickableSingle
import com.photo.starsnap.main.viewmodel.main.UploadViewModel
import com.photo.starsnap.network.star.dto.StarResponseDto
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PickStarScreen(navController: NavController, uploadViewModel: UploadViewModel) {
    var star by remember { mutableStateOf("") }
    var selectStar by remember { mutableStateOf(listOf("")) }
    val starList = uploadViewModel.starList.collectAsLazyPagingItems()
    val gridState = rememberLazyGridState()


    LaunchedEffect(starList) {
        Log.d("PickStarScreen", "starList: ${starList.itemCount}")
    }

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
                    value = star,
                    onValueChange = { star = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text(text = "Star 검색") }
                )
                Spacer(Modifier.width(20.dp))
                Box(
                    Modifier
                        .padding(end = 10.dp)
                        .align(alignment = Alignment.CenterVertically)
                        .clickableSingle {
                            navController.popBackStack()
                            uploadViewModel.setStar(selectStar)
                        }
                ) {
                    Text("확인")
                }
            }
            LazyVerticalGrid(
                state = gridState,
                modifier = Modifier.fillMaxSize().background(CustomColor.success),
                columns = GridCells.Fixed(1),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(starList.itemCount) { index ->
                    val star = starList[index]
                    Log.d("StarList", "index: $index, image: $star")

                    if(star != null){
                        Log.d("PickStarScreen", "추가")
                        StarItem(star){}
                    } else {
                        Box(Modifier.fillMaxWidth()
                            .height(70.dp).background(CustomColor.yellow_100))
                    }
                }
            }
        }
    }
}

@Composable
fun StarItem(
    star: StarResponseDto,
    onClick: () -> Unit
) {
    var onClickState by remember { mutableStateOf(false) }
    var backgroundColor = if (onClickState) CustomColor.yellow_300 else CustomColor.container
    Row(
        Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(shape = RoundedCornerShape(12.dp), color = backgroundColor)
            .clickableSingle { onClickState = !onClickState }
    ) {
        GlideImage(
            modifier = Modifier
                .size(50.dp)
                .padding(start = 18.dp),
            imageModel = { star.imageKey }
        )
        Spacer(Modifier.width(10.dp))
        Column {
            Text(
                text = star.name,
            )
            Text(
                text = star.nickname,
            )
        }
        Spacer(Modifier.weight(1F))
    }
}
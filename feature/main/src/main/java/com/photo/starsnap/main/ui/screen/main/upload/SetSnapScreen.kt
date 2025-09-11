package com.photo.starsnap.main.ui.screen.main.upload

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Divider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.main.ui.component.TopAppBar
import com.photo.starsnap.main.viewmodel.main.UploadViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay

@Composable
fun SetSnapScreen(navController: NavController, uploadViewModel: UploadViewModel) {
    LaunchedEffect(Unit) {
        Log.d("화면", "SetSnapScreen")
    }
    val selectedPhotos by uploadViewModel.selectedPhotos.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { selectedPhotos.size })

    var showDots by remember { mutableStateOf(false) }

    // snap 정보
    var title by remember { mutableStateOf("") } // 제목
    var tags by remember { mutableStateOf(listOf<String>()) } // 태그
    var stars by remember { mutableStateOf(listOf<String>()) } // 스타
    var starGroups by remember { mutableStateOf(listOf<String>()) } // 스타 그룹
    var aiState by remember { mutableStateOf(false) } // AI 여부
    var commentsEnabled by remember { mutableStateOf(true) } // 댓글 허용 여부

    LaunchedEffect(pagerState.isScrollInProgress) {
        if (pagerState.isScrollInProgress) {
            showDots = true
        } else {
            // Hide after a short delay when scrolling stops
            delay(600)
            showDots = false
        }
    }

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
            // 사진 위치 상태
            AnimatedVisibility(
                visible = showDots, enter = fadeIn(), exit = fadeOut()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(selectedPhotos.size) { index ->
                        val isSelected = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(if (isSelected) 10.dp else 8.dp)
                                .background(
                                    color = if (isSelected) CustomColor.light_black else CustomColor.light_gray.copy(
                                        alpha = 0.3f
                                    ), shape = CircleShape
                                )
                        )
                    }
                }
            }
            Spacer(Modifier.height(15.dp))
            TextField( // 제목 입력
                value = title,
                onValueChange = { title = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("제목 추가...") },
            )
            Spacer(Modifier.height(15.dp))
            Divider() // 구분선
            Spacer(Modifier.height(15.dp))

        }
    }
}
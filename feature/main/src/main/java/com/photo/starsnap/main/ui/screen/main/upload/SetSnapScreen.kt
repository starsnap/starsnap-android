package com.photo.starsnap.main.ui.screen.main.upload

import android.util.Log
import android.widget.ToggleButton
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.main.ui.component.TopAppBar
import com.photo.starsnap.main.utils.NavigationRoute
import com.photo.starsnap.main.utils.clickableSingle
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
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 22.dp)
                .verticalScroll(rememberScrollState(), enabled = true, flingBehavior = null),
        ) {
            val starGridState = rememberLazyGridState()
            val starGroupGridState = rememberLazyGridState()
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
            val dotsAlpha by animateFloatAsState(
                targetValue = if (showDots) 1f else 0f,
                animationSpec = tween(durationMillis = 250),
                label = "dotsAlpha"
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .alpha(dotsAlpha), // 공간 유지 + 페이드 애니메이션
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(selectedPhotos.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(if (isSelected) 10.dp else 8.dp)
                            .background(
                                color = if (isSelected) CustomColor.light_black
                                else CustomColor.light_gray.copy(alpha = 0.3f), shape = CircleShape
                            )
                    )
                }
            }
            Spacer(Modifier.height(15.dp))
            Text("제목")
            Spacer(Modifier.height(5.dp))
            TextField(
                // 제목 입력
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("제목 추가...") },
            )
            Spacer(Modifier.height(15.dp))
            Divider() // 구분선
            Spacer(Modifier.height(15.dp))
            Text("Star")
            Spacer(Modifier.height(5.dp))
            LazyHorizontalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                state = starGridState,
                rows = GridCells.Fixed(1),
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(1) {
                    Column(
                        modifier = Modifier
                            .width(50.dp)
                            .height(65.dp)
                            .clickableSingle {
                                navController.navigate(NavigationRoute.PICK_STAR)
                            }) {
                        Box(
                            modifier = Modifier
                                .background(CustomColor.light_gray.copy(alpha = 0.3f))
                                .width(50.dp)
                                .height(50.dp), contentAlignment = Alignment.BottomEnd
                        ) {
                            Text("+")
                        }
                        Spacer(Modifier.height(3.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "추가",
                            maxLines = 1
                        )
                    }
                }
            }
            Spacer(Modifier.height(15.dp))
            Divider() // 구분선
            Spacer(Modifier.height(15.dp))
            Text("StarGroup")
            Spacer(Modifier.height(5.dp))
            LazyHorizontalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(87.dp),
                state = starGroupGridState,
                rows = GridCells.Fixed(1),
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(1) {
                    Column(
                        modifier = Modifier
                            .width(92.dp)
                            .height(69.dp)
                            .clickableSingle {
                                navController.navigate(NavigationRoute.PICK_STAR_GROUP)
                            }) {
                        Box(
                            modifier = Modifier
                                .background(
                                    CustomColor.light_gray.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .width(90.dp)
                                .height(54.dp), contentAlignment = Alignment.BottomEnd
                        ) {
                            Text("+")
                        }
                        Spacer(Modifier.height(3.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "추가하기"
                        )
                    }
                }
            }
            Spacer(Modifier.height(15.dp))
            Divider() // 구분선
            Spacer(Modifier.height(15.dp))
            Text("사진 찍은 날짜")
            Spacer(Modifier.height(5.dp))
            TextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("YYYY-MM-DD") },
            )
            Spacer(Modifier.height(15.dp))
            Divider() // 구분선
            Spacer(Modifier.height(15.dp))
            Row {
                Column {
                    Text("AI 사용 여부")
                    Spacer(Modifier.height(4.dp))
                    Text("AI로 제작된 사진은 체크 해야합니다.")
                }
                Spacer(Modifier.weight(1f))
                Switch(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    checked = aiState,
                    onCheckedChange = {
                        aiState = it
                    }
                )
            }
            Spacer(Modifier.height(15.dp))
            Divider() // 구분선
            Spacer(Modifier.height(15.dp))
            Text("출처")
            Spacer(Modifier.height(5.dp))
            TextField(
                // 출처 입력
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("출처") },
            )
            Spacer(Modifier.height(40.dp))
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(CustomColor.yellow_100),
                contentAlignment = Alignment.Center
            ) {
                Text("다음")
            }
        }
    }
}
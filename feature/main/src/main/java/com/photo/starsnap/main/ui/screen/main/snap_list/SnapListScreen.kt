package com.photo.starsnap.main.ui.screen.main.snap_list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.photo.starsnap.main.ui.component.Snap
import com.photo.starsnap.main.utils.NavigationRoute.SNAP
import com.photo.starsnap.main.viewmodel.main.SnapViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnapListScreen(navController: NavController, viewModel: SnapViewModel) {

    LaunchedEffect(Unit) { Log.d("화면", "SnapListScreen") }

    val snaps = viewModel.snapList.collectAsLazyPagingItems()

    var refreshing by remember { mutableStateOf(false) }
    val pullState = rememberPullToRefreshState()

    // 당겨서 새로고침 트리거: 여기서만 refreshing = true
    val onPullRefresh = {
        refreshing = true
        snaps.refresh()        // 데이터 새로고침
    }

    // 새로고침 완료/실패 시에만 refreshing 종료 (초기 로딩에는 false 유지)
    LaunchedEffect(snaps.loadState.refresh) {
        val state = snaps.loadState.refresh
        if (refreshing && state !is LoadState.Loading) {
            refreshing = false
        }
    }

    Scaffold(containerColor = Color.White) { padding ->
        PullToRefreshBox(
            isRefreshing = refreshing,
            onRefresh = onPullRefresh, // ← 새로 Pager 만들지 말고 refresh()
            state = pullState,
            modifier = Modifier.padding(padding)
        ) {
            when (val state = snaps.loadState.refresh) {
//                is LoadState.Loading -> {
//                    // 첫 로딩 스켈레톤 등
//                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                        CircularProgressIndicator()
//                    }
//                }
                is LoadState.Error -> {
                    val refreshError = state.error
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("불러오기에 실패했어요 😭")
                            Spacer(Modifier.height(4.dp))
                            // 어떤 예외인지 표시
                            Text(text = refreshError.localizedMessage ?: refreshError.toString())

                            Spacer(Modifier.height(8.dp))
                            Button(onClick = {
                                // 마지막 실패 로드를 재시도
                                snaps.retry()
                                // 필요 시 새로운 PagingSource로 완전 새로고침
                                snaps.refresh()
                            }) { Text("다시 시도") }
                        }
                    }
                }

                is LoadState.NotLoading, LoadState.Loading -> {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // ✅ 정석: itemCount + index 접근
                        items(snaps.itemCount) { index ->
                            val snap = snaps[index]
                            if (snap != null) {
                                Snap(snapImageUrl = snap.snapData.imageKey) {
                                    viewModel.selectSnap(snap)
                                    navController.navigate(SNAP)
                                }
                            } else {
                                // placeholder 자리 (enablePlaceholders=true인 경우)
                                Box(
                                    Modifier
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.Gray.copy(alpha = 0.2f))
                                )
                            }
                        }

                        // 추가 로딩(append) 상태 표시 (선택)
                        if (snaps.loadState.append is LoadState.Loading) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) { CircularProgressIndicator() }
                            }
                        }
                        if (snaps.loadState.append is LoadState.Error) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Button(onClick = { snaps.retry() }) { Text("더 불러오기 실패, 다시 시도") }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
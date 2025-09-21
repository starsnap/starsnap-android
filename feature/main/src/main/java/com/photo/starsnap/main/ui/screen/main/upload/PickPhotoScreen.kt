package com.photo.starsnap.main.ui.screen.main.upload

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.photo.starsnap.main.ui.component.SelectImage
import com.photo.starsnap.main.ui.component.PickImageTopAppBar
import com.photo.starsnap.main.utils.NavigationRoute
import com.photo.starsnap.main.viewmodel.main.UploadViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.InternalLandscapistApi
import com.skydoves.landscapist.glide.GlideImage

private fun isPermanentlyDenied(context: Context, permissions: Array<String>): Boolean {
    val activity = context as? Activity ?: return false
    return permissions.any { perm ->
        ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_DENIED &&
                !ActivityCompat.shouldShowRequestPermissionRationale(activity, perm)
    }
}

private fun openAppSettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}

private fun hasAllPermissions(context: Context, permissions: Array<String>): Boolean {
    return permissions.all { perm ->
        ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
    InternalLandscapistApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun PickPhotoScreen(
    navController: NavController,
    uploadViewModel: UploadViewModel
) {

    val TAG = "PickImageScreen"

    val context = LocalContext.current

    // Android 14+ : READ_MEDIA_IMAGES + READ_MEDIA_VISUAL_USER_SELECTED
    // Android 13 : READ_MEDIA_IMAGES
    // Android 12 이하 : READ_EXTERNAL_STORAGE
    val requiredPermissions = when {
        Build.VERSION.SDK_INT >= 34 /* Android 14+ */ -> {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
            )
        }

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU /* 33, Android 13 */ -> {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
        }

        else -> {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    // 권한 상태
    var hasPermission by remember {
        mutableStateOf(
            hasAllPermissions(
                context,
                requiredPermissions
            )
        )
    }

    // 권한 요청
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        hasPermission = result.values.all { it }
    }

    val photoList = uploadViewModel.photoList.collectAsLazyPagingItems() // 사진 목록
    val selectedPhotos by uploadViewModel.selectedPhotos.collectAsStateWithLifecycle() // 선택 사진 목록


    // 권한 허용으로 상태 변경시 사진 가져오도록
    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            photoList.refresh()
        }
    }

    // 처음 화면 진입시 권한 체크 및 요청
    LaunchedEffect(Unit) {
        Log.d("화면", "PickImageScreen")
        hasPermission = hasAllPermissions(context, requiredPermissions)
        // 권한이 없고, 영구 거부도 아닌 경우에만 권한 요청
        if (!hasPermission && !isPermanentlyDenied(context, requiredPermissions)) {
            permissionLauncher.launch(requiredPermissions)
        }
    }

    when (hasPermission) {
        true -> { // 사진 권한이 있을때
            Scaffold(
                topBar = {
                    PickImageTopAppBar(
                        onClose = {
                            navController.popBackStack()
                            uploadViewModel.removeSelectImage()
                        },
                        onNext = {
                            if (selectedPhotos.isNotEmpty()) {
                                navController.navigate(NavigationRoute.SET_SNAP)
                            }
                        }
                    )
                },
            ) { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    val density = LocalDensity.current
                    val minHeaderHeight = 0.dp // 최소 높이
                    val maxHeaderHeight = 400.dp // 최대 높이
                    var headerHeight by remember { mutableStateOf(400.dp) } // view image height
                    val gridState = rememberLazyGridState()
                    val pagerState = rememberPagerState(pageCount = { selectedPhotos.size })
                    val scope = rememberCoroutineScope()
                    val previousSelectedCount = remember { mutableIntStateOf(0) }


                    LaunchedEffect(selectedPhotos) {
                        if (selectedPhotos.size > previousSelectedCount.intValue && selectedPhotos.isNotEmpty()) {
                            // 사진이 추가된 경우, 마지막(새로 추가된) 페이지로 부드럽게 이동
                            pagerState.animateScrollToPage(selectedPhotos.size - 1)
                        }
                        previousSelectedCount.intValue = selectedPhotos.size
                    }

                    val headerNestedScroll = remember {
                        object : NestedScrollConnection {
                            override fun onPreScroll(
                                available: Offset,
                                source: NestedScrollSource
                            ): Offset {
                                if (available.y == 0f) return Offset.Zero
                                val atTop =
                                    gridState.firstVisibleItemIndex == 0 && gridState.firstVisibleItemScrollOffset == 0
                                // Pulling down (expand): only expand header when grid is at TOP.
                                if (available.y > 0f && atTop && headerHeight < maxHeaderHeight) {
                                    val deltaDp = with(density) { available.y.toDp() }
                                    val old = headerHeight
                                    val newHeight =
                                        (headerHeight + deltaDp).coerceAtMost(maxHeaderHeight)
                                    val consumedDp = newHeight - old
                                    if (consumedDp != 0.dp) {
                                        headerHeight = newHeight
                                        val consumedPx = with(density) { consumedDp.toPx() }
                                        return Offset(0f, consumedPx)
                                    }
                                    return Offset.Zero
                                }
                                // Pushing up (collapse): always collapse header first; grid scrolls after header hits MIN.
                                if (available.y < 0f && headerHeight > minHeaderHeight) {
                                    val deltaDp = with(density) { available.y.toDp() } // negative
                                    val old = headerHeight
                                    val newHeight =
                                        (headerHeight + deltaDp).coerceAtLeast(minHeaderHeight)
                                    val consumedDp = newHeight - old
                                    if (consumedDp != 0.dp) {
                                        headerHeight = newHeight
                                        val consumedPx = with(density) { consumedDp.toPx() }
                                        return Offset(0f, consumedPx)
                                    }
                                    return Offset.Zero
                                }
                                // Otherwise, let the grid consume (no header change)
                                return Offset.Zero
                            }
                        }
                    }

                    if (selectedPhotos.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(headerHeight),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = "사진을 선택해 주세요."
                            )
                        }

                    } else {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(headerHeight)
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
                    }

                    // Photo grid: takes the rest, and participates in nested scroll
                    LazyVerticalGrid(
                        state = gridState,
                        modifier = Modifier
                            .weight(1f)                // occupy remaining space with finite height
                            .fillMaxWidth()
                            .nestedScroll(headerNestedScroll),
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(1.dp),
                        verticalArrangement = Arrangement.spacedBy(1.dp)
                    ) {
                        items(photoList.itemCount) { index ->
                            val galleryImage = photoList[index]
                            Log.d("photoList", "index: $index, image: $galleryImage")
                            if (galleryImage != null) {
                                Box(
                                    modifier = Modifier
                                        .padding(1.dp)
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                ) {
                                    val isSelected = selectedPhotos.any { it.id == galleryImage.id }
                                    GlideImage(
                                        modifier = Modifier
                                            .padding(1.dp)
                                            .aspectRatio(1f)
                                            .clickable {
                                                val currentIndex =
                                                    selectedPhotos.indexOfFirst { it.id == galleryImage.id }
                                                if (isSelected && currentIndex != -1) {
                                                    // 선택 해제(삭제) 전에 페이저를 목표 위치로 애니메이션 이동
                                                    val targetPage = if (currentIndex == 0) {
                                                        // 첫번째를 지우면 오른쪽(다음)으로
                                                        if (selectedPhotos.size > 1) 1 else 0
                                                    } else {
                                                        // 그 외에는 왼쪽(이전)으로
                                                        currentIndex - 1
                                                    }
                                                    scope.launch {
                                                        // 더 짧은 애니메이션으로 전환 속도 개선
                                                        val duration = 150 // ms
                                                        val overlap =
                                                            110  // 애니메이션 끝나기 전에 살짝 앞당겨 삭제(해제)

                                                        // 삭제(선택 해제)를 애니메이션 진행 중간쯤에 수행하여 지연감을 줄임
                                                        val deleteJob = launch {
                                                            delay(overlap.toLong())
                                                            uploadViewModel.selectedImage(
                                                                galleryImage.id,
                                                                galleryImage.uri
                                                            )
                                                        }

                                                        pagerState.animateScrollToPage(
                                                            targetPage,
                                                            animationSpec = tween(
                                                                durationMillis = duration,
                                                                easing = FastOutSlowInEasing
                                                            )
                                                        )

                                                        // 혹시 모를 경합을 방지하기 위해 삭제 작업 완료까지 대기
                                                        deleteJob.join()
                                                    }
                                                } else {
                                                    // 새로 선택하는 경우는 바로 추가
                                                    uploadViewModel.selectedImage(
                                                        galleryImage.id,
                                                        galleryImage.uri
                                                    )
                                                }
                                            },
                                        imageModel = { galleryImage.uri },
                                        imageOptions = ImageOptions(
                                            contentScale = ContentScale.Crop,
                                            alignment = Alignment.Center,
                                            alpha = 1f
                                        )
                                    )

                                    if (isSelected) {
                                        SelectImage(selectedPhotos.indexOfFirst { it.id == galleryImage.id })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        false -> { // 사진 권한이 없을때
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val permanentlyDenied = isPermanentlyDenied(context, requiredPermissions)
                    Text("사진 접근 권한이 필요해요")
                    Spacer(Modifier.height(8.dp))

                    if (permanentlyDenied) {
                        Button(onClick = { permissionLauncher.launch(requiredPermissions) }) {
                            Text("권한 허용 요청")
                        }
                    } else {
                        Text("여러 번 거부되어 더 이상 권한 창이 보이지 않아요. 설정에서 권한을 켜주세요.")
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { openAppSettings(context) }) {
                            Text("설정 열기")
                        }
                    }
                }
            }
        }
    }
}
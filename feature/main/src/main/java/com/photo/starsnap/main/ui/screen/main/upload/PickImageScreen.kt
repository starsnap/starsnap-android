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
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.main.ui.component.SelectImage
import com.photo.starsnap.main.ui.component.PickImageTopAppBar
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

@OptIn(ExperimentalMaterial3Api::class, InternalLandscapistApi::class)
@Composable
fun PickImageScreen(
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
    val selectedPhotos by uploadViewModel.selectedPhotos.collectAsStateWithLifecycle()

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

    // 권한이 없을 경우 권한 요청
    if (!hasPermission) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val permanentlyDenied = isPermanentlyDenied(context, requiredPermissions)
                    Text("사진 접근 권한이 필요해요")
                    Spacer(Modifier.height(8.dp))

                    if (!permanentlyDenied) {
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
    } else {
        Scaffold(
            topBar = { PickImageTopAppBar(navController) },
            containerColor = CustomColor.container
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

                val headerNestedScroll = remember {
                    object : NestedScrollConnection {
                        override fun onPreScroll(
                            available: Offset,
                            source: NestedScrollSource
                        ): Offset {
                            if (available.y == 0f) return Offset.Zero
                            val atTop =
                                gridState.firstVisibleItemIndex == 0 && gridState.firstVisibleItemScrollOffset == 0
                            val layoutInfo = gridState.layoutInfo
                            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
                            val total = layoutInfo.totalItemsCount
                            val atBottom = total > 0 && lastVisible >= total - 1
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

                LazyHorizontalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CustomColor.error)
                        .height(headerHeight),
                    rows = GridCells.Fixed(1)
                ) {
                    items(selectedPhotos.size) { index ->
                        val photo = selectedPhotos[index]
                        GlideImage(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxHeight()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {

                                },
                            imageModel = { photo.imageUri },
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.Crop,
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
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(photoList.itemCount) { index ->
                        val galleryImage = photoList[index]
                        if (galleryImage != null) {
                            Box {
                                val isSelected = selectedPhotos.any { it.id == galleryImage.id }
                                GlideImage(
                                    modifier = Modifier
                                        .padding(1.dp)
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                        .animateContentSize()
                                        .clickable {
                                            Log.d(
                                                TAG,
                                                "image id: ${galleryImage.id} click, isSelected: $isSelected"
                                            )
                                            uploadViewModel.addSelectedImage(
                                                id = galleryImage.id,
                                                imageUri = galleryImage.uri
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
    }
}
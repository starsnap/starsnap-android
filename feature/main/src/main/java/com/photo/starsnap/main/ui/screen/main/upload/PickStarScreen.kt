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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.lottie.model.content.CircleShape
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.designsystem.CustomColor.container
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.text.CustomTextStyle.title2
import com.photo.starsnap.main.ui.component.BaseEditText
import com.photo.starsnap.main.ui.component.PasswordEyeCheckBox
import com.photo.starsnap.main.ui.component.TextEditHint
import com.photo.starsnap.main.utils.EditTextType
import com.photo.starsnap.main.utils.clickableSingle
import com.photo.starsnap.main.utils.getKeyboardType
import com.photo.starsnap.main.utils.maxLangth
import com.photo.starsnap.main.viewmodel.main.UploadViewModel
import com.photo.starsnap.network.star.dto.StarResponseDto
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PickStarScreen(navController: NavController, uploadViewModel: UploadViewModel) {
    var star by remember { mutableStateOf("") }
    var selectStars = uploadViewModel.selectedStars.collectAsStateWithLifecycle()
    val starList = uploadViewModel.starList.collectAsLazyPagingItems()
    val gridState = rememberLazyGridState()


    LaunchedEffect(starList) {
        Log.d("PickStarScreen", "starList: ${starList.itemCount}")
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
                    SearchTextField()
                }
                Spacer(Modifier.width(15.dp))
                Text(
                    modifier = Modifier
                        .clickableSingle {
                            navController.popBackStack()
                        }
                        .height(50.dp)
                        .width(40.dp),
                    text = "확인", textAlign = TextAlign.Center
                )
            }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyVerticalGrid(
                state = gridState,
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(1),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (starList.itemCount != 0) {
                    items(starList.itemCount) { index ->
                        val star = starList[index]
                        Log.d("StarList", "index: $index, image: $star")

                        if (star != null) {
                            Log.d("PickStarScreen", "추가")
                            StarItem(star)
                        } else {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(70.dp)
                                    .background(CustomColor.yellow_100)
                            )
                        }
                    }
                } else {
                    items(20) { index ->
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(
                                    shape = RoundedCornerShape(12.dp),
                                    color = CustomColor.container
                                )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StarItem(
    star: StarResponseDto
) {
    var onClickState by remember { mutableStateOf(false) }
    var backgroundColor = if (onClickState) CustomColor.yellow_200 else CustomColor.container
    Row(
        Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(shape = RoundedCornerShape(12.dp), color = backgroundColor)
            .clickableSingle { onClickState = !onClickState },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(16.dp))
        GlideImage(
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape)
                .background(CustomColor.light_gray),
            imageModel = { star.imageKey },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        )
        Spacer(Modifier.width(10.dp))
        Column(
            verticalArrangement = Arrangement.Center
        ) {
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

@Composable
@Preview
fun SearchTextField() {
    var text by remember { mutableStateOf("") }
    BasicTextField(
        value = text,
        textStyle = title2,
        onValueChange = { input ->
            if (100 > input.length) {
                text = input
            }
        },
        modifier = Modifier
            .height(50.dp)
            .background(container, shape = RoundedCornerShape(size = 12.dp)),
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .padding(start = 0.dp, end = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(11.dp))
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.search_icon),
                    contentDescription = "search_icon"
                )
                Spacer(modifier = Modifier.width(9.dp))
                Box(
                    Modifier.weight(1F)
                ) {
                    innerTextField()
                    if (text.isEmpty()) {
                        TextEditHint("Star 검색")
                    }
                }
            }
        })
}
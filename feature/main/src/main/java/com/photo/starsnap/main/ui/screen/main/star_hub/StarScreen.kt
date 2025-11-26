package com.photo.starsnap.main.ui.screen.main.star_hub

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.text.TextFont.pretendard
import com.photo.starsnap.main.ui.component.TopAppBar
import com.photo.starsnap.main.viewmodel.main.StarViewModel
import com.photo.starsnap.main.viewmodel.main.UserViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlin.math.abs

@Composable
@Preview
fun StarScreen(
//    starViewModel: StarViewModel,
//    userViewModel: UserViewModel,
//    rootNavController: NavController,
//    searchHubNavController: NavController
) {
    LaunchedEffect(Unit) {
        Log.d("화면", "StarScreen")
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = "",
                navController = rememberNavController()
            )
        },
        containerColor = CustomColor.container,
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(45.dp))
            GlideImage(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(color = CustomColor.light_gray),
                imageModel = { "" },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "\uD83C\uDF82 2006.05.15 ",style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = CustomColor.sub_title
            ))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "강해린",style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = CustomColor.light_black
            ))
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = "NewJeans",style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = CustomColor.sub_title
            ))
            Spacer(modifier = Modifier.height(30.dp))

        }
    }
}
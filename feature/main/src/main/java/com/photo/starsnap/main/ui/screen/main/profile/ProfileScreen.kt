package com.photo.starsnap.main.ui.screen.main.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.text.CustomTextStyle
import com.photo.starsnap.main.viewmodel.main.UserViewModel
import com.skydoves.landscapist.glide.GlideImage
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProfileScreen(mainNavController: NavController, userViewModel: UserViewModel) {
    LaunchedEffect(Unit) {
        Log.d("화면", "ProfileScreen")
    }
    val user by userViewModel.userData.collectAsStateWithLifecycle()
    Scaffold(
        containerColor = Color.White,
        topBar = {
            ProfileTopBar(mainNavController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            GlideImage(
                modifier = Modifier
                    .background(CustomColor.light_gray, CircleShape)
                    .size(140.dp)
                    .clip(CircleShape),
                imageModel = { user.profileImageUrl },
            )
            Spacer(modifier = Modifier.height(11.dp))
            Text(text = user.username, style = CustomTextStyle.TitleLarge)
            Spacer(modifier = Modifier.height(37.dp))
            FollowStats(followCount = user.followCount, followerCount = user.followerCount)
        }
    }
}

@Composable
fun ProfileTopBar(navController: NavController) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1F))
        Box(
            modifier = Modifier
                .padding(start = 5.dp)
//                .clickableSingle(onClick = { navController.popBackStack() })
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.menu_icon),
                contentDescription = "menu_icon"
            )
        }
    }
}

@Composable
fun FollowStats(followCount: String, followerCount: String) {
    Row(
        modifier = Modifier
            .height(65.dp)
            .width(300.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FollowItem(followCount, "팔로우")
        Spacer(modifier = Modifier.weight(1F))
        FollowItem(followerCount, "팔로워")
        Spacer(modifier = Modifier.weight(1F))
        FollowItem("100", "Snaps")
    }
}

@Composable
fun FollowItem(count: String, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = CustomTextStyle.title1)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = count, style = CustomTextStyle.hint2)
    }
}
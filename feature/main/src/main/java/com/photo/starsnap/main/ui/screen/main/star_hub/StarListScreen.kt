package com.photo.starsnap.main.ui.screen.main.star_hub

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.main.utils.clickableSingle
import com.photo.starsnap.main.viewmodel.main.StarViewModel
import com.photo.starsnap.network.star.dto.StarResponseDto
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun StarListScreen(
    rootNavController: NavController,
    searchHubNavController: NavController,
    starViewModel: StarViewModel
) {
    val starList = starViewModel.starList.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        Log.d("화면", "StarListScreen")
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(starList.itemCount) { index ->
            val star = starList[index]
            if(star != null) {
                StarItem(star) {
                    starViewModel.selectStar(star)
                }
            }
        }
    }

}

@Composable
fun StarItem(star: StarResponseDto, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(color = CustomColor.container, shape = RoundedCornerShape(12.dp))
            .clickableSingle {
                onClick()
            }
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        GlideImage(
            modifier = Modifier
                .background(CustomColor.light_gray, CircleShape)
                .size(50.dp)
                .clip(CircleShape)
                .align(Alignment.CenterVertically),
            imageModel = {  },
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(text = star.name)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = star.explanation ?: "")
        }
    }
}
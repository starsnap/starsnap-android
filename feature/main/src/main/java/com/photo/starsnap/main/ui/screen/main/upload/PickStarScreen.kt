package com.photo.starsnap.main.ui.screen.main.upload

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.photo.starsnap.main.utils.clickableSingle
import com.photo.starsnap.main.viewmodel.main.UploadViewModel

@Composable
fun PickStarScreen(navController: NavController, uploadViewModel: UploadViewModel) {
    var star by remember { mutableStateOf("") }
    var selectStar by remember { mutableStateOf(listOf("")) }

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
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(1)
            ) {
                items(10) {

                }
            }
        }
    }
}
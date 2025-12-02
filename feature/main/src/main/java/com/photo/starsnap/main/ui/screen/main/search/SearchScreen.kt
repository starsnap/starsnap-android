package com.photo.starsnap.main.ui.screen.main.search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun SearchScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        Log.d("화면", "SearchScreen")
    }
    Scaffold(containerColor = Color.White) { padding ->
        Column(Modifier.padding(padding)) {

        }
    }
}
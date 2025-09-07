package com.photo.starsnap.main.ui.screen.main.profile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.photo.starsnap.designsystem.CustomColor

@Composable
fun ProfileScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        Log.d("화면", "ProfileScreen")
    }
    Scaffold(
        containerColor = CustomColor.container
    ) { padding ->
        Column(
            Modifier.padding(padding)
        ) {

        }
    }
}
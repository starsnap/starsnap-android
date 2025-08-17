package com.photo.starsnap.main.ui.screen.main.snap_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.main.ui.component.SnapIcon
import com.photo.starsnap.main.ui.component.SnapImage
import com.photo.starsnap.main.ui.component.SnapInformation
import com.photo.starsnap.main.ui.component.SnapMessages
import com.photo.starsnap.main.ui.component.SnapUser
import com.photo.starsnap.main.ui.component.TopAppBar
import com.photo.starsnap.main.viewmodel.main.SnapViewModel

@Composable
fun SnapScreen(navController: NavController, viewModel: SnapViewModel) {

    val snap = viewModel.snapState.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = stringResource(R.string.snap_top_app_bar_title),
            navController = navController
        )
    }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Snap Image
            SnapImage(snap.value.selectSnap?.snapData?.imageKey)
            Column(
                modifier = Modifier.padding(horizontal = 22.dp)
            ) {
                // Snap Icon(message, save, like)
                SnapIcon()
                // User Information(user image, username)
                SnapUser(
                    profileImageUrl = snap.value.selectSnap?.createdUser?.imageKey,
                    username = snap.value.selectSnap?.createdUser?.username ?: "",
                    createAt = snap.value.selectSnap?.snapData?.createdAt ?: ""
                )
                // snap Information(title, tag, date taken, source)
                SnapInformation(
                    title = snap.value.selectSnap?.snapData?.title ?: "",
                    tags = snap.value.selectSnap?.snapData?.tags ?: emptyList(),
                    dateTaken = snap.value.selectSnap?.snapData?.dateTaken ?: "",
                    source = snap.value.selectSnap?.snapData?.source ?: ""
                )
                SnapMessages(snap.value.selectSnap?.snapData?.comments)
            }
        }
    }
}

package com.photo.starsnap.main.ui.screen.main.snap_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.photo.starsnap.main.ui.component.Snap
import com.photo.starsnap.main.utils.NavigationRoute.SNAP
import com.photo.starsnap.main.viewmodel.main.SnapViewModel

@Composable
fun SnapListScreen(navController: NavController, viewModel: SnapViewModel) {

    val snap = viewModel.snapList.collectAsLazyPagingItems()

    Scaffold { padding ->
        LazyVerticalGrid(
            modifier = Modifier.padding(padding),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(snap.itemSnapshotList.items) { _, snap ->
                Snap(snapImageUrl = snap.snapData.imageKey) {
                    viewModel.selectSnap(snap)
                    navController.navigate(SNAP)
                }
            }
        }
    }
}
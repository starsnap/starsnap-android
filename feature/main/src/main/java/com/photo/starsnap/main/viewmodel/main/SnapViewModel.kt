package com.photo.starsnap.main.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.photo.starsnap.main.utils.constant.Constant.SNAP_SIZE
import com.photo.starsnap.main.utils.paging.SnapPagingSource
import com.photo.starsnap.network.snap.SnapRepository
import com.photo.starsnap.network.snap.dto.SnapResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class SnapViewModel @Inject constructor(
    private val snapRepository: SnapRepository
) : ViewModel() {

    companion object {
        const val TAG = "SnapViewModel"
    }

    val snapList = Pager(
        config = PagingConfig(
            pageSize = SNAP_SIZE,
            enablePlaceholders = false // or false, 취향/요건에 따라
        ),
        pagingSourceFactory = { SnapPagingSource(snapRepository) }
    ).flow.cachedIn(viewModelScope)

    private val _snapState = MutableStateFlow(SnapState())
    val snapState: StateFlow<SnapState> get() = _snapState

    fun selectSnap(snap: SnapResponseDto) {
        _snapState.value = _snapState.value.copy(selectSnap = snap)
    }

    fun createSnap(
        image: RequestBody,
        title: String,
        source: String,
        dateTaken: String,
        aiState: Boolean,
        tag: List<String>,
        starId: List<String>,
        starGroupId: List<String>
    ) = viewModelScope.launch {
        runCatching {
            snapRepository.createSnap(
                image = image,
                title = title,
                source = source,
                dateTaken = dateTaken,
                aiState = aiState,
                tag = tag,
                starId = starId,
                starGroupId = starGroupId
            )
        }.onSuccess {

        }.onFailure {

        }
    }
}

data class SnapState(
    val refreshSnapLoading: Boolean = false, // Snap 새로고침
    val snapListLoading: Boolean = false, // Snap 리스트 로딩
    val selectSnap: SnapResponseDto? = null
)
package com.photo.starsnap.main.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.photo.starsnap.main.utils.constant.Constant.SNAP_SIZE
import com.photo.starsnap.main.utils.paging.SnapPagingSource
import com.photo.starsnap.network.snap.SnapRepository
import com.photo.starsnap.network.snap.dto.SnapResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class SnapViewModel @Inject constructor(
    private val snapRepository: SnapRepository
) : ViewModel() {

    private val _snapList = MutableStateFlow<PagingData<SnapResponseDto>>(PagingData.empty())
    val snapList: StateFlow<PagingData<SnapResponseDto>>
        get() = items

    private val removedItems = MutableStateFlow<Set<String>>(emptySet())
    private val updatedItems = MutableStateFlow<Map<String, SnapResponseDto>>(emptyMap())

    private val items =
        combine(_snapList, removedItems, updatedItems) { all, removed, updated ->
            all.filter { it.snapData.snapId !in removed }
                .map { updated.getOrDefault(key = it.snapData.snapId, defaultValue = it) }
        }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private val _snapState = MutableStateFlow(SnapState())
    val snapState: StateFlow<SnapState> get() = _snapState

    fun selectSnap(snap: SnapResponseDto) {
        _snapState.value = _snapState.value.copy(selectSnap = snap)
    }

    private fun removeSnap(snap: SnapResponseDto) {
        removedItems.update { it + snap.snapData.snapId }
    }

    private fun updateSnap(snap: SnapResponseDto) {
        updatedItems.update { it + (snap.snapData.snapId to snap) }
    }


    fun getSnapList(
        tag: List<String>,
        title: String,
        userId: String,
        starId: List<String>,
        starGroupId: List<String>
    ) = viewModelScope.launch {
        _snapState.value = _snapState.value.copy(snapListLoading = true)

        Pager(
            config = PagingConfig(
                pageSize = SNAP_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                SnapPagingSource(
                    snapRepository = snapRepository,
                    tag = tag,
                    title = title,
                    userId = userId,
                    starId = starId,
                    starGroupId = starGroupId
                )
            }
        ).flow.cachedIn(viewModelScope).collectLatest {
            _snapList.value = it
            _snapState.value = _snapState.value.copy(snapListLoading = false)
        }
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

enum class SnapListLoadingState {
    SUCCESS,          // 성공
    ERROR,            // 실패
    LOADING;          // 로딩
}
package com.photo.starsnap.main.viewmodel.main

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.photo.starsnap.model.photo.dao.GalleryImage
import com.photo.starsnap.main.utils.paging.CustomGalleryPagingSource
import com.photo.starsnap.main.utils.paging.CustomGalleryPagingSource.Companion.PAGING_SIZE
import com.photo.starsnap.model.photo.PhotoRepository
import com.photo.starsnap.network.snap.SnapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val snapRepository: SnapRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _customGalleryPhotoList =
        MutableStateFlow<PagingData<GalleryImage>>(PagingData.empty())
    val customGalleryPhotoList: StateFlow<PagingData<GalleryImage>>
        get() = _customGalleryPhotoList.asStateFlow()

    private var _selectedImage: CroppingImage? = null
    val selectedImage: CroppingImage?
        get() = _selectedImage

    // 핸드폰 갤러리에 있는 사진 패이징 처리해서 가져오기
    fun getGalleryPagingImages() = viewModelScope.launch {
        Log.d("UploadViewModel", "사진 불러오기")
        _customGalleryPhotoList.value = PagingData.empty()
        Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                CustomGalleryPagingSource(
                    photoRepository = photoRepository,
                    currnetLocation = "", // 모든 위치의 사진 가져오기
                )
            },
        ).flow.cachedIn(viewModelScope).collectLatest {
            _customGalleryPhotoList.value = it
        }
    }

    // 사진 선택
    fun addSelectedImage(id: Long, imageUri: Uri) {
        Log.d("UploadViewModel", "image id: $id, select")
        _selectedImage = CroppingImage(id, imageUri)
    }

    // 사진 선택 해제
    private fun removeSelectedImage(id: Long) {
        Log.d("UploadViewModel", "image id: $id, remove")
        if (_selectedImage?.id == id) {
            _selectedImage = null
        }
    }

    fun uploadSnap() = viewModelScope.launch {

    }
}

data class CroppingImage(
    val id: Long,
    val imageUri: Uri,
)
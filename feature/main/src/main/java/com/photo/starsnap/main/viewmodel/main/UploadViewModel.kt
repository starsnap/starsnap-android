package com.photo.starsnap.main.viewmodel.main

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.photo.starsnap.main.utils.constant.Constant.GALLERY_PHOTO_SIZE
import com.photo.starsnap.main.utils.paging.CustomGalleryPagingSource
import com.photo.starsnap.model.photo.PhotoRepository
import com.photo.starsnap.network.snap.SnapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val snapRepository: SnapRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    val photoList = Pager(
        config = PagingConfig(
            pageSize = GALLERY_PHOTO_SIZE,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = {
            CustomGalleryPagingSource(
                photoRepository = photoRepository,
                currnetLocation = "", // 모든 위치의 사진 가져오기
            )
        }
    ).flow.cachedIn(viewModelScope)

    private val _selectedImages = MutableStateFlow<List<CroppingImage>>(emptyList())
    val selectedPhotos: StateFlow<List<CroppingImage>>
        get() = _selectedImages

    // 사진 선택
    fun selectedImage(id: Long, imageUri: Uri) {
        val current = _selectedImages.value
        val exists = current.any { it.id == id }
        _selectedImages.value = if (exists) {
            current.filterNot { it.id == id }
        } else {
            current + CroppingImage(id = id, imageUri = imageUri)
        }
        Log.d(
            "UploadViewModel",
            "image id: $id, ${if (exists) "remove" else "select"}"
        )
    }

    // star 선택
    fun setStar(stars: List<String>){

    }

    // star group 선택
    fun setStarGroup(starGroups: List<String>){

    }

    fun uploadSnap() = viewModelScope.launch {

    }
}

data class CroppingImage(
    val id: Long,
    val imageUri: Uri,
)
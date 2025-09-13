package com.photo.starsnap.main.viewmodel.main

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.photo.starsnap.main.utils.constant.Constant.GALLERY_PHOTO_SIZE
import com.photo.starsnap.main.utils.constant.Constant.STAR_GROUP_SIZE
import com.photo.starsnap.main.utils.constant.Constant.STAR_SIZE
import com.photo.starsnap.main.utils.paging.CustomGalleryPagingSource
import com.photo.starsnap.main.utils.paging.StarGroupPagingSource
import com.photo.starsnap.main.utils.paging.StarPagingSource
import com.photo.starsnap.model.photo.PhotoRepository
import com.photo.starsnap.network.snap.SnapRepository
import com.photo.starsnap.network.star.StarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val snapRepository: SnapRepository,
    private val starRepository: StarRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    val photoList = Pager(
        config = PagingConfig(
            pageSize = GALLERY_PHOTO_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            CustomGalleryPagingSource(
                photoRepository = photoRepository,
                currnetLocation = "" // 모든 위치의 사진 가져오기
            )
        }
    ).flow.cachedIn(viewModelScope)

    val starList = Pager(
        config = PagingConfig(
            pageSize = STAR_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            StarPagingSource(
                starRepository = starRepository
            )
        }
    ).flow.cachedIn(viewModelScope)

    val starGroupList = Pager(
        config = PagingConfig(
            pageSize = STAR_GROUP_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            StarGroupPagingSource(
                starRepository = starRepository
            )
        }
    ).flow.cachedIn(viewModelScope)

    private val _selectedImages = MutableStateFlow<List<CroppingImage>>(emptyList())
    val selectedPhotos: StateFlow<List<CroppingImage>>
        get() = _selectedImages


    private val _selectedStars = MutableStateFlow<List<String>>(emptyList())
    val selectedStars: StateFlow<List<String>>
        get() = _selectedStars


    private val _selectedStarGroups = MutableStateFlow<List<String>>(emptyList())
    val selectedStarGroups: StateFlow<List<String>>
        get() = _selectedStarGroups

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
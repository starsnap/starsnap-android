package com.photo.starsnap.main.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.photo.starsnap.main.utils.constant.Constant.STAR_GROUP_SIZE
import com.photo.starsnap.main.utils.paging.StarGroupPagingSource
import com.photo.starsnap.main.utils.paging.StarPagingSource
import com.photo.starsnap.network.star.StarRepository
import com.photo.starsnap.network.star.dto.StarGroupResponseDto
import com.photo.starsnap.network.star.dto.StarResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class StarViewModel @Inject constructor(
    private val starRepository: StarRepository,
) : ViewModel() {

    private val _searchStarName = MutableStateFlow("")
    private val _searchStarGroupName = MutableStateFlow("")

    private val selectedStarGroup = MutableStateFlow<StarGroupResponseDto?>(null)
    private val selectedStar = MutableStateFlow<StarResponseDto?>(null)

    fun selectStarGroup(starGroup: StarGroupResponseDto) {
        selectedStarGroup.value = starGroup
    }

    fun selectStar(star: StarResponseDto) {
        selectedStar.value = star
    }

    fun setSearchStarName(name: String) {
        _searchStarName.value = name
    }

    fun setSearchStarGroupName(name: String) {
        _searchStarGroupName.value = name
    }

    val starGroupList = Pager(
        config = PagingConfig(
            pageSize = STAR_GROUP_SIZE,
            initialLoadSize = STAR_GROUP_SIZE,
            enablePlaceholders = false
        ), pagingSourceFactory = {
            StarGroupPagingSource(
                starRepository = starRepository, starGroupName = _searchStarGroupName.value
            )
        }).flow.cachedIn(viewModelScope)

    val starList = Pager(
        config = PagingConfig(
            pageSize = STAR_GROUP_SIZE,
            initialLoadSize = STAR_GROUP_SIZE,
            enablePlaceholders = false
        ), pagingSourceFactory = {
            StarPagingSource(
                starRepository = starRepository, starName = _searchStarName.value
            )
        }).flow.cachedIn(viewModelScope)
}
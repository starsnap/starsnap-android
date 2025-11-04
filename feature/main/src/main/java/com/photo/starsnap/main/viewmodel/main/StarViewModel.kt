package com.photo.starsnap.main.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.photo.starsnap.main.utils.constant.Constant.STAR_GROUP_SIZE
import com.photo.starsnap.main.utils.paging.StarGroupPagingSource
import com.photo.starsnap.main.utils.paging.StarPagingSource
import com.photo.starsnap.model.photo.PhotoRepository
import com.photo.starsnap.network.snap.SnapRepository
import com.photo.starsnap.network.star.StarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class StarViewModel @Inject constructor(
    private val starRepository: StarRepository,
) : ViewModel() {

    private val _searchStarName = MutableStateFlow<String>("")
    val searchStarName: StateFlow<String>
        get() = _searchStarName

    private val _searchStarGroupName = MutableStateFlow<String>("")
    val searchStarGroupName: StateFlow<String>
        get() = _searchStarGroupName

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
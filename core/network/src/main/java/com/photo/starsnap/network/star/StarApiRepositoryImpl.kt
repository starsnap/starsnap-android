package com.photo.starsnap.network.star

import com.photo.starsnap.network.dto.SliceResponseDto
import com.photo.starsnap.network.star.dto.StarGroupResponseDto
import com.photo.starsnap.network.star.dto.StarResponseDto
import javax.inject.Inject

class StarApiRepositoryImpl @Inject constructor(
    private val starApi: StarApi
) : StarRepository {
    override suspend fun getStarList(
        page: Int,
        size: Int
    ): SliceResponseDto<StarResponseDto> {
        return starApi.getStarList(page, size)
    }

    override suspend fun getStarGroupList(
        size: Int,
        page: Int
    ): SliceResponseDto<StarGroupResponseDto> {
        return starApi.getStarGroupList(size, page)
    }

}
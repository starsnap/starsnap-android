package com.photo.starsnap.network.star

import com.photo.starsnap.network.dto.SliceResponseDto
import com.photo.starsnap.network.star.dto.StarGroupResponseDto
import com.photo.starsnap.network.star.dto.StarResponseDto
import javax.inject.Inject

class StarApiRepositoryImpl @Inject constructor(
    private val starApi: StarApi
) : StarRepository {
    override suspend fun getStarList(
        size: Int,
        page: Int,
        starName: String
    ): SliceResponseDto<StarResponseDto> {
        return starApi.getStarList(size, page, starName)
    }

    override suspend fun getStarGroupList(
        size: Int,
        page: Int,
        starGroupName: String
    ): SliceResponseDto<StarGroupResponseDto> {
        return starApi.getStarGroupList(size, page, starGroupName)
    }

}
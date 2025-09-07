package com.photo.starsnap.network.snap

import com.photo.starsnap.network.dto.SliceResponseDto
import com.photo.starsnap.network.dto.StatusDto
import com.photo.starsnap.network.snap.dto.SnapDto
import com.photo.starsnap.network.snap.dto.SnapResponseDto
import okhttp3.RequestBody
import javax.inject.Inject

class SnapApiRepositoryImpl @Inject constructor(
    private val snapApi: SnapApi
) : SnapRepository {
    override suspend fun createSnap(
        image: RequestBody,
        title: String,
        source: String,
        dateTaken: String,
        aiState: Boolean,
        tag: List<String>,
        starId: List<String>,
        starGroupId: List<String>
    ) {
        return snapApi.createSnap(
            image,
            title,
            source,
            dateTaken,
            aiState,
            tag,
            starId,
            starGroupId
        )
    }

    override suspend fun sendSnap(size: Int, page: Int) {
        return snapApi.sendSnap(size, page)
    }

    override suspend fun fixSnap(
        snapId: String,
        image: RequestBody?,
        title: String,
        source: String,
        dateTaken: String,
        aiState: Boolean,
        tag: List<String>,
        starId: List<String>,
        starGroupId: List<String>,
    ): SnapDto {
        return snapApi.fixSnap(
            snapId,
            image,
            title,
            source,
            dateTaken,
            aiState,
            tag,
            starId,
            starGroupId
        )
    }

    override suspend fun deleteSnap(snapId: String): StatusDto {
        return snapApi.deleteSnap(snapId)
    }

    override suspend fun getSnap(
        size: Int,
        page: Int,
        tag: List<String>,
        title: String,
        userId: String,
        starId: List<String>,
        starGroupId: List<String>
    ): SliceResponseDto<SnapResponseDto> {
        return snapApi.getSnap(size, page, tag, title, userId, starId, starGroupId)
    }

    override suspend fun getFeedSnap(
        page: Int,
        size: Int
    ): SliceResponseDto<SnapResponseDto> {
        return snapApi.getFeedSnap(page, size)
    }
}
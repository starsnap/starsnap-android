package com.photo.starsnap.network.snap

import com.photo.starsnap.network.dto.SliceResponseDto
import com.photo.starsnap.network.dto.StatusDto
import com.photo.starsnap.network.snap.dto.SnapDto
import com.photo.starsnap.network.snap.dto.SnapResponseDto
import okhttp3.RequestBody

interface SnapRepository {
    suspend fun createSnap(
        image: RequestBody,
        title: String,
        source: String,
        dateTaken: String,
        aiState: Boolean,
        tag: List<String>,
        starId: List<String>,
        starGroupId: List<String>
    )

    suspend fun sendSnap(size: Int, page: Int)

    suspend fun fixSnap(
        snapId: String,
        image: RequestBody?,
        title: String,
        source: String,
        dateTaken: String,
        aiState: Boolean,
        tag: List<String>,
        starId: List<String>,
        starGroupId: List<String>
    ): SnapDto

    suspend fun deleteSnap(snapId: String): StatusDto

    suspend fun getSnap(
        size: Int,
        page: Int,
        tag: List<String>,
        title: String,
        userId: String,
        starId: List<String>,
        starGroupId: List<String>
    ): SliceResponseDto<SnapResponseDto>

    suspend fun getFeedSnap(
        page: Int,
        size: Int
    ): SliceResponseDto<SnapResponseDto>
}
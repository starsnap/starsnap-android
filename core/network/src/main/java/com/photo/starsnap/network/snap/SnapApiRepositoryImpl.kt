package com.photo.starsnap.network.snap

import com.photo.starsnap.network.dto.StatusDto
import com.photo.starsnap.network.snap.dto.SnapDto
import okhttp3.RequestBody
import javax.inject.Inject

class SnapApiRepositoryImpl @Inject constructor(
    val snapApi: SnapApi
): SnapRepository {
    override fun createSnap(
        image: RequestBody,
        title: String,
        source: String,
        dateTaken: String
    ) {
        return snapApi.createSnap(image, title, source, dateTaken)
    }

    override fun sendSnap(size: Int, page: Int) {
        return snapApi.sendSnap(size, page)
    }

    override fun fixSnap(
        snapId: String,
        image: RequestBody?,
        title: String,
        source: String,
        dateTaken: String,
    ): SnapDto {
        return snapApi.fixSnap(snapId, image, title, source, dateTaken)
    }

    override fun deleteSnap(snapId: String): StatusDto {
        return snapApi.deleteSnap(snapId)
    }
}
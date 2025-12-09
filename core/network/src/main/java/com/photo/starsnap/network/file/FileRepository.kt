package com.photo.starsnap.network.file

import com.photo.starsnap.network.file.dto.rq.UploadFileRequestDto
import com.photo.starsnap.network.file.dto.rs.UploadFileResponseDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header


interface FileRepository {
    suspend fun createPhotoPresidentUrl(
        uploadRequest: UploadFileRequestDto
    ): Response<UploadFileResponseDto>

    suspend fun createVideoPresidentUrl(
        uploadRequest: UploadFileRequestDto
    ): Response<UploadFileResponseDto>

    suspend fun uploadFile(
        presignedUrl: String,
        contentType: String,
        aiState: Boolean,
        dateTaken: String,
        source: String,
        userId: String,
        file: RequestBody
    )
}
package com.photo.starsnap.network.file

import com.photo.starsnap.network.file.dto.rq.UploadFileRequestDto
import com.photo.starsnap.network.file.dto.rs.UploadFileResponseDto
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class FileApiRepositoryImpl @Inject constructor(
    private val fileApi: FileApi
): FileRepository {
    override suspend fun createPhotoPresidentUrl(uploadRequest: UploadFileRequestDto): Response<UploadFileResponseDto> {
        return fileApi.createPhotoPresidentUrl(uploadRequest)
    }

    override suspend fun createVideoPresidentUrl(uploadRequest: UploadFileRequestDto): Response<UploadFileResponseDto> {
        return fileApi.createVideoPresidentUrl(uploadRequest)
    }

    override suspend fun uploadFile(
        presignedUrl: String,
        contentType: String,
        aiState: Boolean,
        dateTaken: String,
        source: String,
        userId: String,
        file: RequestBody
    ) {
        return fileApi.uploadFile(
            presignedUrl,
            contentType,
            aiState,
            dateTaken,
            source,
            userId,
            file
        )
    }

}
package com.photo.starsnap.network.file

import com.photo.starsnap.network.file.dto.rq.UploadFileRequestDto
import com.photo.starsnap.network.file.dto.rs.UploadFileResponseDto
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

}
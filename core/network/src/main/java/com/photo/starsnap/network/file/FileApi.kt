package com.photo.starsnap.network.file

import com.photo.starsnap.network.file.dto.rq.UploadFileRequestDto
import com.photo.starsnap.network.file.dto.rs.UploadFileResponseDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Url

interface FileApi {
    @POST("/api/file/photo")
    suspend fun createPhotoPresidentUrl(
        @Body uploadRequest: UploadFileRequestDto
    ): Response<UploadFileResponseDto>

    @POST("/api/file/video")
    suspend fun createVideoPresidentUrl(
        @Body uploadRequest: UploadFileRequestDto
    ): Response<UploadFileResponseDto>

    @PUT
    suspend fun uploadFile(
        @Url presignedUrl: String, // Presigned URL 전체가 Endpoint가 됨
        @Header("Content-Type") contentType: String,
        @Header("x-amz-meta-ai-state") aiState: Boolean,
        @Header("x-amz-meta-date-taken") dateTaken: String,
        @Header("x-amz-meta-source") source: String,
        @Header("x-amz-meta-user-id") userId: String,
        @Body file: RequestBody
    )
}
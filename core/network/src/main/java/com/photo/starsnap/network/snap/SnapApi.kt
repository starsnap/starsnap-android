package com.photo.starsnap.network.snap

import com.photo.starsnap.network.dto.SliceResponseDto
import com.photo.starsnap.network.dto.StatusDto
import com.photo.starsnap.network.snap.dto.SnapDto
import com.photo.starsnap.network.snap.dto.SnapResponseDto
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface SnapApi {
    @POST("/api/snap/create") // snap 생성
    suspend fun createSnap(
        @Part("image") image: RequestBody,
        @Part("title") title: String,
        @Part("source") source: String,
        @Part("date-taken") dateTaken: String,
        @Part("ai-state") aiState: Boolean,
        @Part("tag") tag: List<String>,
        @Part("star-id") starId: List<String>,
        @Part("star-group-id") starGroupId: List<String>
    )

    @GET("/api/snap/send") // snap 조회
    @Headers("Auth: false")
    suspend fun sendSnap(@Query("size") size: Int, @Query("page") page: Int)

    @PATCH("/api/snap/fix") // snap 수정
    suspend fun fixSnap(
        @Part("snap-id") snapId: String,
        @Part("image") image: RequestBody?,
        @Part("title") title: String,
        @Part("source") source: String,
        @Part("date-taken") dateTaken: String,
        @Part("ai-state") aiState: Boolean,
        @Part("tag") tag: List<String>,
        @Part("star-id") starId: List<String>,
        @Part("star-group-id") starGroupId: List<String>
    ): SnapDto

    @GET("/api/snap/feed") // snap 기본 조회
    suspend fun getFeedSnap(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): SliceResponseDto<SnapResponseDto>


    @DELETE("/api/snap/delete") // snap 삭제
    suspend fun deleteSnap(
        @Query("snap-id") snapId: String
    ): StatusDto

    @GET("/api/snap")
    suspend fun getSnap(
        size: Int,
        page: Int,
        tag: List<String>,
        title: String,
        userId: String,
        starId: List<String>,
        starGroupId: List<String>
    ): SliceResponseDto<SnapResponseDto>

}
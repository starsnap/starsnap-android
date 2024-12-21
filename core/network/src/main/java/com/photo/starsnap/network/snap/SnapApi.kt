package com.photo.starsnap.network.snap

import com.photo.starsnap.network.dto.StatusDto
import com.photo.starsnap.network.snap.dto.SnapDto
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
    fun createSnap(
        @Part("image") image: RequestBody,
        @Part("title") title: String,
        @Part("source") source: String,
        @Part("date-taken") dateTaken: String
    )

    @GET("/api/snap/send") // snap 조회
    @Headers("Auth: false")
    fun sendSnap(@Query("size") size: Int, @Query("page") page: Int)

    @PATCH("/api/snap/fix") // snap 수정
    fun fixSnap(
        @Part("snap-id") snapId: String,
        @Part("image") image: RequestBody?,
        @Part("title") title: String,
        @Part("source") source: String,
        @Part("date-taken") dateTaken: String,
    ): SnapDto


    @DELETE("/api/snap/delete") // snap 삭제
    fun deleteSnap(
        @Query("snap-id") snapId: String,
    ): StatusDto

}
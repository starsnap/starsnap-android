package com.photo.android.network.snap

import com.photo.android.network.dto.StatusDto
import com.photo.android.network.snap.dto.SnapDto
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface SnapApi {
    @POST("/snap/create") // snap 생성
    fun createSnap(
        @Part("image") image: RequestBody,
        @Part("title") title: String,
        @Part("source") source: String,
    )

    @GET("/snap/send") // snap 조회
    @Headers("Auth: false")
    fun sendSnap(@Query("size") size: Int, @Query("page") page: Int)

    @PATCH("/snap/fix") // snap 수정
    fun fixSnap(
        @Part("snap-id") snapId: String,
        @Part("image") image: RequestBody?,
        @Part("title") title: String,
        @Part("source") source: String,
        @Part("date-taken") dateTaken: String,
    ): SnapDto


    @DELETE("/snap/delete") // snap 삭제
    fun deleteSnap(
        @Query("snap-id") snapId: String,
    ): StatusDto

}
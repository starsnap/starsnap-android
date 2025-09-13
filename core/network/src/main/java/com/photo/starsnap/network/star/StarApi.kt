package com.photo.starsnap.network.star

import com.photo.starsnap.network.dto.SliceResponseDto
import com.photo.starsnap.network.star.dto.StarGroupResponseDto
import com.photo.starsnap.network.star.dto.StarResponseDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface StarApi {
    @GET("/api/star")
    suspend fun getStarList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): SliceResponseDto<StarResponseDto>


    @GET("/api/star-group")
    suspend fun getStarGroupList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): SliceResponseDto<StarGroupResponseDto>
}
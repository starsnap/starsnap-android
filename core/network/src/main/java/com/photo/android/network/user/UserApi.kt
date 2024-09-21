package com.photo.android.network.user

import com.photo.android.network.dto.SliceResponseDto
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @POST("/user/follow")
    fun follow(@Query("user-id") userId: String)
    @POST("/user/unfollow")
    fun unfollow(@Query("user-id") userId: String)

//    @GET("/user/follow")
//    fun follow(@Query("page") page: Int, @Query("size") size: Int): SliceResponseDto<>
//    @GET("/user/follower")
//    fun follower(@Query("page") page: Int, @Query("size") size: Int): SliceResponseDto<>


    @PATCH("/user/change-username")
    fun changeUsername(@Query("username") username: String)
}
package com.photo.starsnap.network.user

import com.photo.starsnap.network.dto.SliceResponseDto
import com.photo.starsnap.network.dto.StatusDto
import com.photo.starsnap.network.user.dto.Follow
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface UserApi {
    @POST("/user/follow") // 팔로우
    fun follow(@Query("user-id") userId: String): StatusDto

    @POST("/user/unfollow") // 언팔로우
    fun unfollow(@Query("user-id") userId: String): StatusDto


    @GET("/user/follow") // 팔로우 정보 확인
    fun getFollowData(@Query("page") page: Int, @Query("size") size: Int): SliceResponseDto<Follow>

    @GET("/user/follower") // 팔로워 정보 확인
    fun getFollowerData(@Query("page") page: Int, @Query("size") size: Int): SliceResponseDto<Follow>


    @PATCH("/user/change-username") // 유저 이름 변경
    fun changeUsername(@Query("username") username: String): StatusDto

    @PATCH("/user/change-profile-image") // 유저 프로필 사진 변경
    fun changeProfileImage(@Part("image") image: MultipartBody.Part): StatusDto
}
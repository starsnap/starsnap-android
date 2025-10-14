package com.photo.starsnap.network.user

import com.photo.starsnap.network.dto.SliceResponseDto
import com.photo.starsnap.network.dto.StatusDto
import com.photo.starsnap.network.user.dto.Follow
import com.photo.starsnap.network.user.dto.GetUserRequest
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface UserApi {
    @POST("/api/user/follow") // 팔로우
    suspend fun follow(@Query("user-id") userId: String): StatusDto

    @POST("/api/user/unfollow") // 언팔로우
    suspend fun unfollow(@Query("user-id") userId: String): StatusDto


    @GET("/api/user/follow") // 팔로우 정보 확인
    suspend fun getFollowData(@Query("page") page: Int, @Query("size") size: Int): SliceResponseDto<Follow>

    @GET("/api/user/follower") // 팔로워 정보 확인
    suspend fun getFollowerData(@Query("page") page: Int, @Query("size") size: Int): SliceResponseDto<Follow>


    @PATCH("/api/user/change-username") // 유저 이름 변경
    suspend fun changeUsername(@Query("username") username: String): StatusDto

    @PATCH("/api/user/change-profile-image") // 유저 프로필 사진 변경
    suspend fun changeProfileImage(@Part("image") image: MultipartBody.Part): StatusDto

    @GET("/api/user/get") // 유저 정보 가져오기
    suspend fun getUserData(): GetUserRequest
}
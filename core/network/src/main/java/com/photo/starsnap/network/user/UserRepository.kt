package com.photo.starsnap.network.user

import com.photo.starsnap.network.dto.SliceResponseDto
import com.photo.starsnap.network.dto.StatusDto
import com.photo.starsnap.network.user.dto.Follow
import com.photo.starsnap.network.user.dto.GetUserRequest
import okhttp3.MultipartBody

interface UserRepository {
    suspend fun follow(userId: String): StatusDto
    suspend fun unfollow(userId: String): StatusDto

    suspend fun getFollowData(page: Int, size: Int): SliceResponseDto<Follow>
    suspend fun getFollowerData(page: Int, size: Int): SliceResponseDto<Follow>

    suspend fun changeUsername(username: String): StatusDto
    suspend fun changeProfileImage(image: MultipartBody.Part): StatusDto

    suspend fun getUserData(): GetUserRequest
}
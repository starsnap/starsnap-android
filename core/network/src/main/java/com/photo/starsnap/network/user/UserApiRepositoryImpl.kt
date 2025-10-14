package com.photo.starsnap.network.user

import com.photo.starsnap.network.dto.SliceResponseDto
import com.photo.starsnap.network.dto.StatusDto
import com.photo.starsnap.network.user.dto.Follow
import com.photo.starsnap.network.user.dto.GetUserRequest
import okhttp3.MultipartBody
import javax.inject.Inject

class UserApiRepositoryImpl @Inject constructor(
    private val userApi: UserApi
): UserRepository {
    override suspend fun follow(userId: String): StatusDto {
        return userApi.follow(userId)
    }

    override suspend fun unfollow(userId: String): StatusDto {
        return userApi.unfollow(userId)
    }

    override suspend fun getFollowData(
        page: Int,
        size: Int,
    ): SliceResponseDto<Follow> {
        return userApi.getFollowData(page, size)
    }

    override suspend fun getFollowerData(
        page: Int,
        size: Int,
    ): SliceResponseDto<Follow> {
        return userApi.getFollowerData(page, size)
    }

    override suspend fun changeUsername(username: String): StatusDto {
        return userApi.changeUsername(username)
    }

    override suspend fun changeProfileImage(image: MultipartBody.Part): StatusDto {
        return userApi.changeProfileImage(image)
    }

    override suspend fun getUserData(): GetUserRequest {
        return userApi.getUserData()
    }
}
package com.photo.android.network.user

import com.photo.android.network.dto.SliceResponseDto
import com.photo.android.network.dto.StatusDto
import com.photo.android.network.user.dto.Follow
import okhttp3.MultipartBody
import javax.inject.Inject

class UserApiRepositoryImpl @Inject constructor(
    val userApi: UserApi
): UserRepository {
    override fun follow(userId: String): StatusDto {
        return userApi.follow(userId)
    }

    override fun unfollow(userId: String): StatusDto {
        return userApi.unfollow(userId)
    }

    override fun getFollowData(
        page: Int,
        size: Int,
    ): SliceResponseDto<Follow> {
        return userApi.getFollowData(page, size)
    }

    override fun getFollowerData(
        page: Int,
        size: Int,
    ): SliceResponseDto<Follow> {
        return userApi.getFollowerData(page, size)
    }

    override fun changeUsername(username: String): StatusDto {
        return userApi.changeUsername(username)
    }

    override fun changeProfileImage(image: MultipartBody.Part): StatusDto {
        return userApi.changeProfileImage(image)
    }
}
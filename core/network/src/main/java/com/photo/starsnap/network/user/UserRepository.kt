package com.photo.starsnap.network.user

import com.photo.starsnap.network.dto.SliceResponseDto
import com.photo.starsnap.network.dto.StatusDto
import com.photo.starsnap.network.user.dto.Follow
import okhttp3.MultipartBody

interface UserRepository {
    fun follow(userId: String): StatusDto
    fun unfollow(userId: String): StatusDto

    fun getFollowData(page: Int, size: Int): SliceResponseDto<Follow>
    fun getFollowerData(page: Int, size: Int): SliceResponseDto<Follow>

    fun changeUsername(username: String): StatusDto
    fun changeProfileImage(image: MultipartBody.Part): StatusDto
}
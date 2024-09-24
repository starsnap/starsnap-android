package com.photo.android.network.user.dto

data class Follow(
    val id: String,
    val createdAt: String,
    val followUserId: String,
    val userId: String
)
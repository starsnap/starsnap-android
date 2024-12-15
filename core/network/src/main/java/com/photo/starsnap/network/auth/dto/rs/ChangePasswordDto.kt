package com.photo.starsnap.network.auth.dto.rs

data class ChangePasswordDto(
    val userId: String,
    val password: String,
    val newPassword: String
)
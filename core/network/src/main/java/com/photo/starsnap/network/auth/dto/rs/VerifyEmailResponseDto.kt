package com.photo.starsnap.network.auth.dto.rs

data class VerifyEmailResponseDto(
    val email: String,
    val token: String
)

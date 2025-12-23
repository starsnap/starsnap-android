package com.photo.starsnap.network.token.dto.rs

data class TokenDto(
    val accessToken: String,
    val refreshToken: String,
    val expiredAt: String,
    val authority: String
)

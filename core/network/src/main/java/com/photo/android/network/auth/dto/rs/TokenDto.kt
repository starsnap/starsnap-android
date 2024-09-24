package com.photo.android.network.auth.dto.rs

import java.time.LocalDateTime

data class TokenDto(
    val accessToken: String,
    val refreshToken: String,
    val expiredAt: LocalDateTime,
    val authority: String
)

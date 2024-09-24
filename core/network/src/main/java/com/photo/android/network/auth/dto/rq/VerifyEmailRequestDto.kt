package com.photo.android.network.auth.dto.rq

data class VerifyEmailRequestDto(
    val email: String,
    val verifyCode: String
)

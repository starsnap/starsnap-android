package com.photo.starsnap.network.auth.dto.rq

data class VerifyEmailRequestDto(
    val email: String,
    val verifyCode: String
)

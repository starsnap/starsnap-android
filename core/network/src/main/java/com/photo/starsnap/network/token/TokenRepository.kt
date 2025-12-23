package com.photo.starsnap.network.token


import com.photo.starsnap.network.auth.dto.rs.TokenDto

interface TokenRepository {
    suspend fun reissueToken(refreshToken: String, accessToken: String): TokenDto
}
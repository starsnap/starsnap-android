package com.photo.starsnap.network.token

import com.photo.starsnap.network.auth.dto.rs.TokenDto
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH

interface TokenApi {
    @PATCH("/api/auth/refresh") // 토큰 재발급
    @Headers("Auth: false")
    suspend fun reissueToken(
        @Header("refresh-token") refreshToken: String,
        @Header("access-token") accessToken: String,
    ): TokenDto

}

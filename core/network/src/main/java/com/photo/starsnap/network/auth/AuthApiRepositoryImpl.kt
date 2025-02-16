package com.photo.starsnap.network.auth

import com.photo.starsnap.network.auth.dto.rq.LoginDto
import com.photo.starsnap.network.auth.dto.rq.SignupDto
import com.photo.starsnap.network.auth.dto.rq.VerifyEmailRequestDto
import com.photo.starsnap.network.auth.dto.rs.ChangePasswordDto
import com.photo.starsnap.network.auth.dto.rs.TokenDto
import com.photo.starsnap.network.auth.dto.rs.VerifyEmailResponseDto
import com.photo.starsnap.network.dto.StatusDto
import javax.inject.Inject


class AuthApiRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
): AuthRepository {
    override suspend fun send(email: String): StatusDto {
        return authApi.send(email)
    }

    override suspend fun verify(verifyEmailRequestDto: VerifyEmailRequestDto): VerifyEmailResponseDto {
        return authApi.verify(verifyEmailRequestDto)
    }

    override suspend fun login(loginDto: LoginDto): TokenDto {
        return authApi.login(loginDto)
    }

    override suspend fun signup(signupDto: SignupDto): StatusDto {
        return authApi.signup(signupDto)
    }

    override suspend fun delete(): StatusDto {
        return authApi.delete()
    }

    override suspend fun reissueToken(
        refreshToken: String,
        accessToken: String,
    ): retrofit2.Response<TokenDto> {
        return authApi.reissueToken(refreshToken, accessToken)
    }

    override suspend fun changePassword(changePasswordDto: ChangePasswordDto): StatusDto {
        return authApi.changePassword(changePasswordDto)
    }

    override suspend fun checkValidUserName(username: String): StatusDto {
        return authApi.checkValidUsername(username)
    }

    override suspend fun checkValidEmail(email: String): StatusDto {
        return authApi.checkValidEmail(email)
    }
}
package com.photo.android.network.auth

import com.photo.android.network.auth.dto.rq.LoginDto
import com.photo.android.network.auth.dto.rq.SignupDto
import com.photo.android.network.auth.dto.rq.VerifyEmailRequestDto
import com.photo.android.network.auth.dto.rs.ChangePasswordDto
import com.photo.android.network.auth.dto.rs.TokenDto
import com.photo.android.network.auth.dto.rs.VerifyEmailResponseDto
import com.photo.android.network.dto.StatusDto
import javax.inject.Inject


class AuthApiRepositoryImpl @Inject constructor(
    val authApi: AuthApi
): AuthRepository {
    override fun send(email: String): StatusDto {
        return authApi.send(email)
    }

    override fun verify(verifyEmailRequestDto: VerifyEmailRequestDto): VerifyEmailResponseDto {
        return authApi.verify(verifyEmailRequestDto)
    }

    override fun login(loginDto: LoginDto): TokenDto {
        return authApi.login(loginDto)
    }

    override fun signup(signupDto: SignupDto): StatusDto {
        return authApi.signup(signupDto)
    }

    override fun delete(): StatusDto {
        return authApi.delete()
    }

    override fun reissueToken(
        refreshToken: String,
        accessToken: String,
    ): TokenDto {
        return authApi.reissueToken(refreshToken, accessToken)
    }

    override fun changePassword(changePasswordDto: ChangePasswordDto): StatusDto {
        return authApi.changePassword(changePasswordDto)
    }
}
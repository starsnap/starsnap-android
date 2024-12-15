package com.photo.starsnap.network.auth

import com.photo.starsnap.network.auth.dto.rq.LoginDto
import com.photo.starsnap.network.auth.dto.rq.SignupDto
import com.photo.starsnap.network.auth.dto.rq.VerifyEmailRequestDto
import com.photo.starsnap.network.auth.dto.rs.ChangePasswordDto
import com.photo.starsnap.network.auth.dto.rs.TokenDto
import com.photo.starsnap.network.auth.dto.rs.VerifyEmailResponseDto
import com.photo.starsnap.network.dto.StatusDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("/auth/email/send") // 이메일 인증번호 전송
    @Headers("Auth: false")
    fun send(@Query("email") email: String): StatusDto

    @POST("/auth/verify") // 인증번호 확인
    @Headers("Auth: false")
    fun verify(@Body verifyEmailRequestDto: VerifyEmailRequestDto): VerifyEmailResponseDto

    @POST("/auth/login") // 로그인
    @Headers("Auth: false")
    fun login(@Body loginDto: LoginDto): TokenDto

    @POST("/auth/signup") // 회원가입
    @Headers("Auth: false")
    fun signup(@Body signupDto: SignupDto): StatusDto

    @DELETE("/auth") // 유저 삭제
    fun delete(): StatusDto

    @PATCH("/auth/refresh") // 토큰 제발급
    @Headers("Auth: false")
    fun reissueToken(
        @Header("refresh-token") refreshToken: String,
        @Header("access-token") accessToken: String,
    ): retrofit2.Response<TokenDto>

    @PATCH("/auth/pw-change") // 비밀번호 변경
    @Headers("Auth: false")
    fun changePassword(
        @Body changePasswordDto: ChangePasswordDto,
    ): StatusDto
}

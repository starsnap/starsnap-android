package com.photo.starsnap.di

import android.util.Log
import com.photo.starsnap.datastore.TokenManager
import com.photo.starsnap.network.token.TokenRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import dagger.Lazy

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val tokenRepository: Lazy<TokenRepository>
) : Authenticator {

    companion object {
        private const val TAG = "AuthAuthenticator"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d(TAG, response.toString())
        // Auth: false이면 jwt token 없이 전달
        if (response.request.header("Auth") == "false") {
            return response.request.newBuilder()
                .removeHeader("Auth")
                .build()
        }

        if(response.code != 401)
            return response.request.newBuilder()
                .build()

        return runBlocking {
            val refreshToken = tokenManager.getRefreshToken().first()
            val accessToken = tokenManager.getAccessToken().first()

            runCatching {
                tokenRepository.get().reissueToken(refreshToken, accessToken)
            }.fold(
                onSuccess = {
                    tokenManager.saveAccessToken(it.accessToken)
                    tokenManager.saveRefreshToken(it.refreshToken)

                    val newToken = "Bearer ${it.accessToken}"
                    response.request.newBuilder()
                        .header("Authorization", newToken)
                        .build()
                },
                onFailure = {
                    // 실패 시: 로그 찍고 데이터 삭제 후 null 반환
                    Log.d("TAG", "Refresh Token 만료")
                    tokenManager.deleteData()
                    null
                }
            )
        }
    }
}
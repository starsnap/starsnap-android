package com.photo.starsnap.di

import android.util.Log
import com.photo.starsnap.datastore.TokenManager
import com.photo.starsnap.di.Url.BASE_URL
import com.photo.starsnap.network.auth.AuthApi
import com.photo.starsnap.network.auth.dto.rs.TokenDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
) : Authenticator {

    companion object {
        private const val TAG = "AuthAuthenticator"
        var expiredRefreshToken: MutableStateFlow<Boolean> = MutableStateFlow(false)
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking { tokenManager.getRefreshToken().first() }
        val accessToken = runBlocking { tokenManager.getAccessToken().first() }
        return runBlocking {
            val reissueToken = reissueToken(refreshToken, accessToken)

            if (!reissueToken.isSuccessful || reissueToken.body() == null) {
                Log.d(TAG, "Refresh Token 만료")
                tokenManager.deleteData()
                expiredRefreshToken.value = true
                return@runBlocking null
            }

            reissueToken.body()?.let {
                tokenManager.saveAccessToken(it.accessToken)
                tokenManager.saveRefreshToken(it.refreshToken)

                val token = "Bearer ${it.accessToken}"

                response.request.newBuilder()
                    .header("Authorization", token)
                    .build()
            }
        }
    }

    private suspend fun reissueToken(
        refreshToken: String,
        accessToken: String
    ): retrofit2.Response<TokenDto> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthApi::class.java)
        return service.reissueToken(refreshToken, accessToken)
    }

}
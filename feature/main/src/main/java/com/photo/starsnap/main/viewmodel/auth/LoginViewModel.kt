package com.photo.starsnap.main.viewmodel.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photo.starsnap.datastore.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.photo.starsnap.network.auth.AuthRepository
import com.photo.starsnap.network.auth.dto.rq.LoginDto

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    companion object {
        private const val TAG = "LoginViewModel"
    }

    fun login(username: String, password: String) = viewModelScope.launch {
        runCatching {
            authRepository.login(LoginDto(username, password))
        }.onSuccess {
            Log.d(TAG, it.toString())
            tokenManager.saveAccessToken(it.accessToken)
            tokenManager.saveRefreshToken(it.refreshToken)
        }.onFailure {

        }
    }
}
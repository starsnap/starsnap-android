package com.photo.starsnap.main.viewmodel.auth

import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.photo.starsnap.datastore.TokenManager
import com.photo.starsnap.main.viewmodel.state.AutoLoginState
import com.photo.starsnap.main.viewmodel.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.photo.starsnap.network.auth.AuthRepository
import com.photo.starsnap.network.auth.dto.rq.LoginDto
import com.photo.starsnap.network.token.TokenRepository
import kotlinx.coroutines.flow.first

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _loginState = MutableLiveData(LoginState.Idle)
    val loginState: LiveData<LoginState> = _loginState

    private val _autoLoginState = MutableLiveData(AutoLoginState.Idle)
    val autoLoginState: LiveData<AutoLoginState> = _autoLoginState

    companion object {
        private const val TAG = "LoginViewModel"
    }

    fun reissueToken() = viewModelScope.launch {
        _autoLoginState.value = AutoLoginState.Loading
        val refreshToken = tokenManager.getRefreshToken().first()
        val accessToken = tokenManager.getAccessToken().first()
        runCatching {
            tokenRepository.reissueToken(refreshToken, accessToken)
        }.onSuccess {
            Log.d(TAG, it.toString())
            tokenManager.saveAccessToken(it.accessToken)
            tokenManager.saveRefreshToken(it.refreshToken)
            tokenManager.saveExpiredAt(it.expiredAt)
            _autoLoginState.value = AutoLoginState.Success
        }.onFailure {
            Log.e(TAG, "reissueToken error", it)
            _autoLoginState.value = AutoLoginState.Failure
        }
    }

    fun login(username: String, password: String) = viewModelScope.launch {
        _loginState.value = LoginState.Loading
        runCatching {
            authRepository.login(LoginDto(username, password))
        }.onSuccess {
            Log.d(TAG, it.toString())
            tokenManager.saveAccessToken(it.accessToken)
            tokenManager.saveRefreshToken(it.refreshToken)
            tokenManager.saveExpiredAt(it.expiredAt)
            _loginState.value = LoginState.Success
        }.onFailure {
            Log.e(TAG, "login error", it)
            _loginState.value = LoginState.Failure
        }
    }

    fun handleSignIn(result: GetCredentialResponse) {
        val credential = result.credential
        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val googleIdToken = googleIdTokenCredential.idToken

                        Log.d(OAuthViewModel.TAG, googleIdToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(OAuthViewModel.TAG, "Received an invalid google id token response", e)
                        _loginState.value = LoginState.Failure
                    } catch (e: Exception) {
                        Log.e(OAuthViewModel.TAG, e.toString())
                        _loginState.value = LoginState.Failure
                    }
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e(OAuthViewModel.TAG, "Unexpected type of credential")
                _loginState.value = LoginState.Failure
            }
        }
    }
}
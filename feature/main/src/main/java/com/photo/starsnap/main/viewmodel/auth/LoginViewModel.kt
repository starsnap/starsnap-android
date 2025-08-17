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
import com.photo.starsnap.main.viewmodel.state.LoginState
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

    private val _loginState = MutableLiveData(LoginState.Idle)
    val loginState: LiveData<LoginState> = _loginState

    companion object {
        private const val TAG = "LoginViewModel"
    }

    fun login(username: String, password: String) = viewModelScope.launch {
        _loginState.value = LoginState.Loading
        runCatching {
            authRepository.login(LoginDto(username, password))
        }.onSuccess {
            Log.d(TAG, it.toString())
            tokenManager.saveAccessToken(it.accessToken)
            tokenManager.saveRefreshToken(it.refreshToken)
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
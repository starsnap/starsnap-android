package com.photo.starsnap.main.viewmodel.auth

import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
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
                    } catch (e: Exception) {
                        Log.e(OAuthViewModel.TAG, e.toString())
                    }
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e(OAuthViewModel.TAG, "Unexpected type of credential")
            }
        }
    }
}
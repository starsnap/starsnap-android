package com.photo.starsnap.main.viewmodel.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photo.starsnap.network.auth.AuthRepository
import com.photo.starsnap.network.auth.dto.rq.SignupDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    companion object {
        const val TAG = "SignupViewModel"
    }

    fun signup(username: String, password: String, email: String, token: String) =
        viewModelScope.launch {
            runCatching {
                authRepository.signup(SignupDto(username, password, email, token))
            }.onSuccess {
                Log.d(TAG, it.toString())
            }.onFailure {

            }
        }
}
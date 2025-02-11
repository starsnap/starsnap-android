package com.photo.starsnap.main.viewmodel.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
ㅎimport com.photo.starsnap.main.utils.TextPattern
import com.photo.starsnap.network.auth.AuthRepository
import com.photo.starsnap.network.auth.dto.rq.SignupDto
import com.photo.starsnap.network.auth.dto.rq.VerifyEmailRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    companion object {
        const val TAG = "SignupViewModel"
    }

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> get() = _uiState

    // 회원가입
    fun signup() =
        viewModelScope.launch {
            val currentState = _uiState.value
            _uiState.value = _uiState.value.copy(signupState = State.LOADING)
            runCatching {
                authRepository.signup(
                    SignupDto(
                        currentState.username,
                        currentState.password,
                        currentState.email,
                        currentState.token
                    )
                )
            }.onSuccess {
                Log.d(TAG, it.toString())
                _uiState.value = _uiState.value.copy(signupState = State.SUCCESS)
            }.onFailure { e ->
                Log.d(TAG, e.message.toString())
                _uiState.value = _uiState.value.copy(signupState = State.ERROR)
            }
        }

    // 닉네임 유효성 검사
    fun checkValidUserName(username: String) = viewModelScope.launch {
        runCatching {
            authRepository.checkValidUserName(username)
            _uiState.value = _uiState.value.copy(usernameValidState = State.LOADING)
        }.onSuccess {
            _uiState.value = _uiState.value.copy(username = username)
            _uiState.value = _uiState.value.copy(usernameButtonState = true)
            _uiState.value = _uiState.value.copy(usernameValidState = State.SUCCESS)
            Log.d(TAG, it.toString())
        }.onFailure { e ->
            _uiState.value = _uiState.value.copy(usernameButtonState = false)
            _uiState.value = _uiState.value.copy(usernameValidState = State.ERROR)
            Log.d(TAG, e.message.toString())
        }
    }

    // 이메일 유효성 검사
    fun checkValidEmail(email: String) = viewModelScope.launch {
        runCatching {
            authRepository.checkValidEmail(email)
            _uiState.value = _uiState.value.copy(emailSendValidState = State.LOADING)
        }.onSuccess {
            _uiState.value = _uiState.value.copy(email = email)
            _uiState.value = _uiState.value.copy(emailSendValidState = State.SUCCESS)
            _uiState.value = _uiState.value.copy(emailSendButtonState = true)
            Log.d(TAG, it.toString())
        }.onFailure { e ->
            _uiState.value = _uiState.value.copy(emailSendButtonState = false)
            _uiState.value = _uiState.value.copy(emailSendValidState = State.ERROR)
            Log.d(TAG, e.message.toString())
        }
    }

    // 인증 번호 발송
    fun sendEmail() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(emailSendValidState = State.LOADING)
        runCatching {
            authRepository.send(_uiState.value.email)
        }.onSuccess {
            _uiState.value = _uiState.value.copy(emailSendValidState = State.SUCCESS)
            Log.d(TAG, it.toString())
        }.onFailure { e ->
            Log.d(TAG, e.message.toString())
            _uiState.value = _uiState.value.copy(emailSendValidState = State.ERROR)
        }
    }

    // 인증 번호 확인
    fun verify(email: String, verifyCode: String) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(verifyState = State.LOADING)
        runCatching {
            authRepository.verify(VerifyEmailRequestDto(email, verifyCode))
        }.onSuccess {
            Log.d(TAG, it.toString())
            _uiState.value = _uiState.value.copy(token = it.token)
            _uiState.value = _uiState.value.copy(verifyState = State.SUCCESS)
        }.onFailure { e ->
            Log.d(TAG, e.message.toString())
            _uiState.value = _uiState.value.copy(verifyState = State.ERROR)
        }
    }

    // 비밀번호 유효성 검사
    fun checkValidPassword(password: String) {
        if (TextPattern.PASSWORD.toPattern().matcher(password).matches()) {
            _uiState.value = _uiState.value.copy(password = password)
            _uiState.value = _uiState.value.copy(passwordButtonState = true)
            _uiState.value = _uiState.value.copy(passwordValidState = State.SUCCESS)
        } else {
            _uiState.value = _uiState.value.copy(password = "")
            _uiState.value = _uiState.value.copy(passwordButtonState = false)
            _uiState.value = _uiState.value.copy(passwordValidState = State.ERROR)
        }
    }
}

data class SignupUiState(
    val username: String = "", // 닉네임
    val password: String = "", // 비밀번호
    val email: String = "", // 이메일
    val verifyCode: String = "", // 인증번호
    val token: String = "", // 회원가입 토큰
    val usernameButtonState: Boolean = false,
    val usernameValidState: State = State.DEFAULT,
    val passwordButtonState: Boolean = false,
    val passwordValidState: State = State.DEFAULT,
    val emailSendButtonState: Boolean = false,
    val emailSendValidState: State = State.DEFAULT,
    val verifyButtonState: Boolean = false,
    val verifyState: State = State.DEFAULT,
    val consentButtonState: Boolean = false,
    val signupButtonState: Boolean = false,
    val signupState: State = State.DEFAULT,
)

enum class State {
    SUCCESS,
    LOADING,
    ERROR,
    DEFAULT
}
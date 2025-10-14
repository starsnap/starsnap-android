package com.photo.starsnap.main.viewmodel.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photo.starsnap.network.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    companion object {
        const val TAG = "UserViewModel"
    }

    private val _userData = MutableStateFlow<UserData>(UserData())
    val userData = _userData.asStateFlow()

    fun setUserData(user: UserData) {
        _userData.value = user
    }

    fun clearUserData() {
        _userData.value = UserData()
    }

    fun getUserData() = viewModelScope.launch {
        runCatching {
            userRepository.getUserData()
        }.onSuccess {
            _userData.value = UserData(
                id = it.userId,
                username = it.username,
                email = it.email,
                authority = it.authority,
                followerCount = it.followerCount.toString(),
                followCount = it.followingCount.toString(),
                profileImageUrl = it.profileImageUrl.toString()
            )
            Log.d(TAG, "User data fetched: ${_userData.value}")
        }.onFailure {
            Log.d(TAG, "Failed to fetch user data", it)
            clearUserData()
        }
    }
}

data class UserData(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val authority: String = "",
    val followerCount: String = "",
    val followCount: String = "",
    val profileImageUrl: String = ""
)
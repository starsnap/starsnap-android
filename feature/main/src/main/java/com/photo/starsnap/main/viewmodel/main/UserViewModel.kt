package com.photo.starsnap.main.viewmodel.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class UserViewModel: ViewModel() {

    private val _userData = MutableStateFlow<UserData>(UserData())
    val userData = _userData.asStateFlow()

    fun setUserData(user: UserData) {
        _userData.value = user
    }

    fun clearUserData() {
        _userData.value = UserData()
    }

}

data class UserData(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val authority: String = "",
    val followerCount: String = "",
    val followCount: String = "",
    val profileImageUrl: String = "",
    val saveCount: String = ""
)
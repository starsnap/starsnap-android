package com.photo.starsnap.main.viewmodel.state

enum class LoginState {
    Success, // 로그인 성공
    Failure, // 로그인 실패
    Idle, // 로그인 시도 전
    Loading // 로그인 시도 중
}
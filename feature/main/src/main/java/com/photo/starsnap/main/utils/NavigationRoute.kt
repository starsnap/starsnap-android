package com.photo.starsnap.main.utils

object NavigationRoute {
    const val AUTH_ROUTE = "auth_route"
    const val MAIN_ROUTE = "main_route"

    const val LOGIN = "login_route"
    const val SIGNUP = "signup_route"

    const val MAIN = "main"

    const val SIGNUP_USERNAME = "auth/signup/username"
    const val SIGNUP_PASSWORD = "auth/signup/password"
    const val SIGNUP_EMAIL = "auth/signup/email"
    const val SIGNUP_VERIFY = "auth/signup/verify"
    const val SIGNUP_CONSENT = "auth/signup/consent"
    const val SIGNUP_LOADING = "auth/signup/loading"

    // snap list screen(처음 화면)
    const val SNAP_LIST = "main/home/snap_list"

    // snap screen
    const val SNAP = "snap"

    // star, star group screen
    const val STAR = "star"
    const val STAR_GROUP = "star_group"

    // star hub
    const val STAR_GROUP_LIST = "main/star_hub/star_group_list" // star group 리스트
    const val STAR_LIST = "main/star_hub/star_list" // star 리스트

    // setting
    const val PROFILE = "profile" // 프로필
    const val FIX_PROFILE = "fix_profile" // 프로필 수정
    const val REPORT_LIST = "main/setting/report_list" // 신고 내역
    const val BLOCK_LIST = "main/setting/block_list" // 차단 내역
    const val SETTING = "main/setting/setting" // 설정
    const val SAVE_LIST = "main/setting/save_list" // 저장 내역
    const val AUTH_SETTING = "main/setting/auth_setting" // 소셜 계정


    // star upload
    const val PICK_STAR = "main/upload/pick_star" // star 선택
    const val SET_SNAP = "main/upload/set_snap" // snap 설정
    const val PICK_STAR_GROUP = "main/upload/pick_star_group" // star 선택
    const val PICK_IMAGE = "main/upload/pick_image" // image 선택

    // 알람
    const val ALARM = "main/alarm"
    const val ALARM_SETTING = "main/setting/alarm_setting" // alarm 설정


    // bottom nav route
    const val HOME_ROUTE = "home_route" // home 라우터
    const val UPLOAD_ROUTE = "upload_route" // star upload 라우터
    const val USER_ROUTE = "user_route" // user 라우터
    const val STAR_HUB_ROUTE = "star_hub_route" // star hub 라우터
    const val ADD_SNAP_ROUTE = "add_snap_route" // add snap 라우터

    // setting route
    const val SETTING_ROUTE = "setting_route" // setting 라우터
}
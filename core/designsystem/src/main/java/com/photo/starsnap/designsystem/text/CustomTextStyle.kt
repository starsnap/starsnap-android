package com.photo.starsnap.designsystem.text

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.photo.starsnap.designsystem.text.TextFont.pretendard
import com.photo.starsnap.designsystem.CustomColor

object CustomTextStyle {
    val TitleLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = CustomColor.light_black
    )
    val TitleMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = CustomColor.light_black
    )
    val TitleSmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        color = CustomColor.light_black
    )

    val title1 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = CustomColor.light_black
    )
    val title2 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = CustomColor.light_black
    )
    val title3 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        color = CustomColor.light_black
    )
    val title4 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        color = CustomColor.gray
    )

    val hint2 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        color = CustomColor.gray
    )

    val hint1 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        color = CustomColor.gray
    )

    val TopBarTitle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        color = CustomColor.light_black
    )

    val SignupTitle = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        color = CustomColor.light_black
    )
}
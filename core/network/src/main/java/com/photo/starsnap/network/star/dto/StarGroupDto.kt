package com.photo.starsnap.network.star.dto

import java.time.LocalDateTime

data class CreateStarGroupRequestDto(
    val name: String, // 이름
    val debutDate: LocalDateTime, // 데뷔일
    val explanation: String?, // 설명
    val starGroupType: StarGroupType // 그룹 타입
)

data class UpdateStarGroupRequestDto(
    val id: String,
    val name: String, // 이름
    val debutDate: LocalDateTime, // 데뷔일
    val explanation: String?, // 설명
    val starGroupType: StarGroupType? // 그룹 타입
)

data class StarGroupResponseDto(
    val name: String,
    val debutDate: LocalDateTime,
    val explanation: String?,
    val starGroupType: StarGroupType
)


data class StarGroupImageResponseDto(
    val imageKey: String
)

enum class StarGroupType {
    BOYGROUP, GIRLGROUP, COEDGROUP
}
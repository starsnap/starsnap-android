package com.photo.starsnap.network.snap.dto

data class SnapDto(
    val snapId: String,
    val title: String,
    val createdAt: String,
    val imageKey: String,
    val tags: List<String>,
    val source: String,
    val type: String,
    val size: Long,
    val dateTaken: String,
    val comments: List<CommentDto>
)


data class SnapResponseDto(
    val createdUser: SnapUserDto,
    val snapData: SnapDto
)

data class SnapUserDto(
    val username: String,
    val imageKey: String?
)

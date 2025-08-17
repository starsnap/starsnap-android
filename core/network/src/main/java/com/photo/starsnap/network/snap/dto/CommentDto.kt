package com.photo.starsnap.network.snap.dto

import java.time.LocalDateTime

data class CommentDto(
    val profileKey: String?,
    val username: String,
    val content: String,
    val createdAt: LocalDateTime?,
    val modifiedAt: LocalDateTime?
)
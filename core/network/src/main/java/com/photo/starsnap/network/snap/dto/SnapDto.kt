package com.photo.starsnap.network.snap.dto

data class SnapDto(
    val snapId: String,
    val title: String,
    val createdAt: String,
    val username: String,
    val imageKey: String,
    val source: String,
    val type: String,
    val size: Long,
    val dateTaken: String
)

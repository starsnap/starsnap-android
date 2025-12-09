package com.photo.starsnap.network.file.dto.rq

data class UploadFileRequestDto(
    val aiState: Boolean,
    val dateTaken: String,
    val source: String
)

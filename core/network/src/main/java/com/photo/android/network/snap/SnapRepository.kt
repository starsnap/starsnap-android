package com.photo.android.network.snap

import com.photo.android.network.dto.StatusDto
import com.photo.android.network.snap.dto.SnapDto
import okhttp3.RequestBody

interface SnapRepository {
    fun createSnap(image: RequestBody, title: String, source: String, dateTaken: String)
    fun sendSnap(size: Int, page: Int)
    fun fixSnap(snapId: String, image: RequestBody?, title: String, source: String, dateTaken: String): SnapDto
    fun deleteSnap(snapId: String): StatusDto
}
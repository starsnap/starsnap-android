package com.photo.starsnap.network.star

import okhttp3.RequestBody
import javax.inject.Inject

class StarApiRepositoryImpl @Inject constructor(
    private val starApi: StarApi
) : StarRepository {

}
package com.photo.starsnap.model.photo

import com.photo.starsnap.model.photo.dao.GalleryImage


interface PhotoRepository {
    fun getAllPhotos(
        page: Int,
        loadSize: Int,
        currentLocation: String? =null,
    ): MutableList<GalleryImage>

    fun getFolderList(): ArrayList<String>

}
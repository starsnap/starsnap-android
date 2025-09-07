package com.photo.starsnap.main.utils.constant

object Constant {
    const val SNAP_SIZE = 50
    const val GALLERY_PHOTO_SIZE = 50
    private const val REGION = "ap-northeast-2"
    private const val BUCKET_NAME = "starsnap"

    fun getImageUrl(imageKey: String?) = "https://$BUCKET_NAME.s3.$REGION.amazonaws.com/$imageKey"
}
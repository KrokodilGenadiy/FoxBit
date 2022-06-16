package com.zaus_app.foxbit.data.entity

import android.provider.MediaStore


data class Song(
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val duration: Long,
    val path: String
)
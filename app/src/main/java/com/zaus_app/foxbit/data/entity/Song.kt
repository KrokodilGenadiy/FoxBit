package com.zaus_app.foxbit.data.entity

import android.provider.MediaStore


data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val data: String,
    val date: Long
)
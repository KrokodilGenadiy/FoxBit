package com.zaus_app.foxbit.data.entity

data class Song(
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val duration: Long,
    val path: String,
)
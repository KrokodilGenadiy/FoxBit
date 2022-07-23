package com.zaus_app.foxbit

import android.app.Application
import com.zaus_app.foxbit.data.entity.Song

class App : Application() {
    var songList: List<Song> = emptyList()

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
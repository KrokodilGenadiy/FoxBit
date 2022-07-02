package com.zaus_app.foxbit.view.rv_viewholders

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.zaus_app.foxbit.data.entity.Song
import com.zaus_app.foxbit.databinding.SongItemBinding

class SongsViewHolder(binding: SongItemBinding, clickAtPosition: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    private val title = binding.songTitle
    private val artist = binding.songArtist
    private val button = binding.more

    init {
        binding.root.setOnClickListener {
            clickAtPosition(adapterPosition)
        }
    }

    fun bind(song: Song) {
        title.text = song.title
        if (song.artist == "<unknown>")
            //TODO: should get string from resources
            artist.text = "Unknown"
        else
            artist.text = song.artist
        button.setOnClickListener{
            Log.i("test","My name is Gyoubu Masataka Oniwa")
        }

    }
}
package com.zaus_app.foxbit.view.rv_viewholders

import androidx.recyclerview.widget.RecyclerView
import com.zaus_app.foxbit.data.entity.Song
import com.zaus_app.foxbit.databinding.SongItemBinding

class SongsViewHolder(binding: SongItemBinding, clickAtPosition: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    private val title = binding.songTitle
    private val artist = binding.songArtist
    private val pack = binding.pack

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
        //pack.text = song.pack

    }
}
package com.zaus_app.foxbit.view.rv_viewholders

import androidx.recyclerview.widget.RecyclerView
import com.zaus_app.foxbit.data.entity.Song
import com.zaus_app.foxbit.databinding.SongItemBinding

class SongsViewHolder(binding: SongItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val title = binding.songTitle
    private val artist = binding.songArtist


    fun bind(song: Song) {
        title.text = song.title
        artist.text = song.artist
    }
}
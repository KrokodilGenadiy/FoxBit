package com.zaus_app.foxbit.view.rv_viewholders

import androidx.recyclerview.widget.RecyclerView
import com.zaus_app.foxbit.data.entity.Album
import com.zaus_app.foxbit.databinding.AlbumItemBinding


class AlbumViewHolder(binding: AlbumItemBinding,clickAtPosition: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    private val title = binding.songTitle
    private val artist = binding.songArtist


    init {
        binding.root.setOnClickListener {
            clickAtPosition(adapterPosition)
        }
    }

    fun bind(album: Album) {
        title.text = album.title
        if (album.artist == "<unknown>")
        //TODO: should get string from resources
            artist.text = "Unknown"
        else
            artist.text = album.artist
    }
}
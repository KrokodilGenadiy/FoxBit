package com.zaus_app.foxbit.view.rv_viewholders

import androidx.recyclerview.widget.RecyclerView
import com.zaus_app.foxbit.data.entity.Album
import com.zaus_app.foxbit.data.entity.Song
import com.zaus_app.foxbit.databinding.AlbumItemBinding
import com.zaus_app.foxbit.databinding.SongItemBinding

class AlbumViewHolder(binding: AlbumItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val title = binding.songTitle
    private val artist = binding.songArtist


    fun bind(album: Album) {
        title.text = album.title
        if (album.artist == "<unknown>")
        //TODO: should get string from resources
            artist.text = "Unknown"
        else
            artist.text = album.artist
        //pack.text = song.pack
    }
}
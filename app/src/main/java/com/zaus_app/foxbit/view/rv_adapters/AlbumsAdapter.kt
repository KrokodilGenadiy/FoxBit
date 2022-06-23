package com.zaus_app.foxbit.view.rv_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaus_app.foxbit.data.entity.Album
import com.zaus_app.foxbit.databinding.AlbumItemBinding
import com.zaus_app.foxbit.view.rv_viewholders.AlbumViewHolder

class AlbumsAdapter(private val clickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = mutableListOf<Album>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = AlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding) {
            clickListener.click(items[it])
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlbumViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    fun getItems(): MutableList<Album> {
        return items
    }

    fun setItems(list: MutableList<Album>) {
        this.items = list
    }

    interface OnItemClickListener {
        fun click(album: Album)
    }
}
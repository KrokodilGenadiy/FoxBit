package com.zaus_app.foxbit.view.rv_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaus_app.foxbit.data.entity.Song
import com.zaus_app.foxbit.databinding.SongItemBinding
import com.zaus_app.foxbit.view.rv_viewholders.SongsViewHolder

class SongsAdapter(private val clickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = mutableListOf<Song>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = SongItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongsViewHolder(binding) {
            clickListener.click(items[it])
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SongsViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    fun getItems(): MutableList<Song> {
        return items
    }

    fun setItems(list: MutableList<Song>) {
        this.items = list
    }

    interface OnItemClickListener {
        fun click(song: Song)
    }


}
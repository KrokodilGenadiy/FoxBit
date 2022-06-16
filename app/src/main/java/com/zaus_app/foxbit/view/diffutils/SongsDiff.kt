package com.zaus_app.moviefrumy.view.rv_adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.zaus_app.foxbit.data.entity.Song

class SongsDiff(val oldList: MutableList<Song>, val newList: MutableList<Song>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title &&
                oldList[oldItemPosition].artist == newList[newItemPosition].artist &&
                oldList[oldItemPosition].path == newList[newItemPosition].path
    }

}
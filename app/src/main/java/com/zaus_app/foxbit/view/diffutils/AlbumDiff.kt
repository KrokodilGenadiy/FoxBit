package com.zaus_app.foxbit.view.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.zaus_app.foxbit.data.entity.Album

class AlbumDiff(val oldList: MutableList<Album>, val newList: MutableList<Album>) :
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
                oldList[oldItemPosition].album_id == newList[newItemPosition].album_id
    }
}
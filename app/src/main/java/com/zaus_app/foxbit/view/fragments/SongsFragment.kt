package com.zaus_app.foxbit.view.fragments

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaus_app.foxbit.R
import com.zaus_app.foxbit.data.entity.Song
import com.zaus_app.foxbit.databinding.FragmentSongsBinding
import com.zaus_app.foxbit.view.rv_adapters.SongsAdapter
import com.zaus_app.moviefrumy.view.rv_adapters.diffutils.SongsDiff
import java.util.ArrayList


class SongsFragment : Fragment() {
    private var _binding: FragmentSongsBinding? = null
    private val binding get() = _binding!!
    private val songsAdapter by lazy {
        SongsAdapter(object : SongsAdapter.OnItemClickListener {
            override fun click(song: Song) {
                // (requireActivity() as MainActivity).launchDetailsFragment(film)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.songsRecycler.apply {
            adapter = songsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        updateData(getSongsFromPhone())

    }

    fun getSongsFromPhone(): ArrayList<Song> {
        val arrayList = ArrayList<Song>()
        val contentResolver = requireActivity().contentResolver
        val songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val songCursor = contentResolver?.query(songUri, null, null, null, null)

        if (songCursor != null && songCursor.moveToFirst()) {
            val songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val songData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            val dateIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED)
            while (songCursor.moveToNext()) {
                val currentId = songCursor.getLong(songId)
                val currentTitle = songCursor.getString(songTitle)
                val currentArtist = songCursor.getString(songArtist)
                val currentData = songCursor.getString(songData)
                val currentDate = songCursor.getLong(dateIndex)
                arrayList.add(Song(currentId, currentTitle, currentArtist, currentData, currentDate))
            }
        }
        return arrayList
    }

    fun updateData(newList: MutableList<Song>){
        val oldList = songsAdapter.getItems()
        val productDiff = SongsDiff(oldList,newList)
        val diffResult = DiffUtil.calculateDiff(productDiff)
        songsAdapter.setItems(newList)
        diffResult.dispatchUpdatesTo(songsAdapter)
    }
}
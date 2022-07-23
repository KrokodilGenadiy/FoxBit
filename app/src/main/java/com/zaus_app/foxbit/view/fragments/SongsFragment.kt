package com.zaus_app.foxbit.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaus_app.foxbit.App
import com.zaus_app.foxbit.view.MainActivity
import com.zaus_app.foxbit.data.entity.Song
import com.zaus_app.foxbit.databinding.FragmentSongsBinding
import com.zaus_app.foxbit.view.rv_adapters.SongsAdapter
import com.zaus_app.moviefrumy.view.rv_adapters.diffutils.SongsDiff


class SongsFragment : Fragment() {
    private var _binding: FragmentSongsBinding? = null
    private val binding get() = _binding!!
    private var songs = App.instance.songList
    private val songsAdapter by lazy {
        SongsAdapter(object : SongsAdapter.OnItemClickListener {
            override fun click(position: Int) {
                 (requireActivity() as MainActivity).launchPlayerFragment(position)
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
        //TODO causes lag when navigation to Home in navigaiton drawer
        if (songs.isEmpty()) {
            App.instance.songList = (requireActivity() as MainActivity).getAllSongs()
            songs = App.instance.songList
        }
        updateData(songs)
    }

    private fun updateData(newList: List<Song>){
        val oldList = songsAdapter.getItems()
        val productDiff = SongsDiff(oldList,newList)
        val diffResult = DiffUtil.calculateDiff(productDiff)
        songsAdapter.setItems(newList)
        diffResult.dispatchUpdatesTo(songsAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
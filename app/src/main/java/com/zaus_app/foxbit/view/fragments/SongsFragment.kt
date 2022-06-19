package com.zaus_app.foxbit.view.fragments

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaus_app.foxbit.MainActivity
import com.zaus_app.foxbit.data.entity.Song
import com.zaus_app.foxbit.databinding.FragmentSongsBinding
import com.zaus_app.foxbit.view.rv_adapters.SongsAdapter
import com.zaus_app.moviefrumy.view.rv_adapters.diffutils.SongsDiff
import java.io.File
import kotlin.collections.ArrayList


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
        updateData((requireActivity() as MainActivity).getAllSongs())
    }

    private fun updateData(newList: MutableList<Song>){
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
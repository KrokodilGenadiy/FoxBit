package com.zaus_app.foxbit.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaus_app.foxbit.MainActivity
import com.zaus_app.foxbit.R
import com.zaus_app.foxbit.data.entity.Album
import com.zaus_app.foxbit.data.entity.Song
import com.zaus_app.foxbit.databinding.FragmentAlbumsBinding
import com.zaus_app.foxbit.databinding.FragmentSongsBinding
import com.zaus_app.foxbit.view.diffutils.AlbumDiff
import com.zaus_app.foxbit.view.rv_adapters.AlbumsAdapter
import com.zaus_app.foxbit.view.rv_adapters.SongsAdapter
import com.zaus_app.moviefrumy.view.rv_adapters.diffutils.SongsDiff

class AlbumsFragment : Fragment() {
    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!
    private val albumsAdapter by lazy {
        AlbumsAdapter(object : AlbumsAdapter.OnItemClickListener {
            override fun click(album: Album) {
                // (requireActivity() as MainActivity).launchDetailsFragment(film)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.albumsRecycler.apply {
            adapter = albumsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        //TODO causes lag when navigation to Home in naviagation drawer
       updateData((requireActivity() as MainActivity).getAllAlbums())
    }

    private fun updateData(newList: MutableList<Album>){
        val oldList = albumsAdapter.getItems()
        val productDiff = AlbumDiff(oldList,newList)
        val diffResult = DiffUtil.calculateDiff(productDiff)
        albumsAdapter.setItems(newList)
        diffResult.dispatchUpdatesTo(albumsAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
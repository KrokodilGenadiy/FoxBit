package com.zaus_app.foxbit.view.fragments

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zaus_app.foxbit.data.entity.Song
import com.zaus_app.foxbit.databinding.FragmentPlayerBinding


class PlayerFragment : Fragment() {
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private lateinit var song: Song
    private var _mediaPlayer: MediaPlayer? = null
    private val mediaPlayer get() = _mediaPlayer!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        song = arguments?.get("song") as Song
        if (_mediaPlayer == null)
            _mediaPlayer = MediaPlayer()
        mediaPlayer.reset()
        mediaPlayer.setDataSource(song.path)
        mediaPlayer.prepare()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSongDetails(song)
        binding.playButton.setOnClickListener{
            mediaPlayer.start()
        }
    }

    fun setSongDetails(song: Song) {
        binding.songTitle.text = this.song.title
        binding.albumTitle.text = song.album
    }

}
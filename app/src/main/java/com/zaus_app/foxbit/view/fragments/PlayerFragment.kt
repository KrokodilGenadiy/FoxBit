package com.zaus_app.foxbit.view.fragments

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.zaus_app.foxbit.App
import com.zaus_app.foxbit.data.entity.Song
import com.zaus_app.foxbit.databinding.FragmentPlayerBinding
import com.zaus_app.foxbit.view.viewmodels.PlayerViewModel


class PlayerFragment : Fragment() {
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private lateinit var songs: List<Song>
    private var position: Int = 0
    private var _mediaPlayer: MediaPlayer? = null
    private val mediaPlayer get() = _mediaPlayer!!
    private val viewModel: PlayerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        songs =  App.instance.songList
        position = arguments?.get("position") as Int
        if (_mediaPlayer == null)
            _mediaPlayer = MediaPlayer()
        mediaPlayer.reset()
        mediaPlayer.setDataSource(songs[position].path)
        mediaPlayer.prepare()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSongDetails(songs[position])
        binding.playButton.setOnClickListener {
            if (viewModel.isPlaying) {
                mediaPlayer.pause()
                viewModel.isPlaying = false
            } else {
                mediaPlayer.start()
                viewModel.isPlaying = true
            }

        }
    }

    fun setSongDetails(song: Song) {
        binding.songTitle.text = song.title
        binding.albumTitle.text = song.album
    }

}
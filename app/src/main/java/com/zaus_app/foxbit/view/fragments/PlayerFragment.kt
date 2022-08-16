package com.zaus_app.foxbit.view.fragments

import android.app.Dialog
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zaus_app.foxbit.App
import com.zaus_app.foxbit.R
import com.zaus_app.foxbit.data.entity.Song
import com.zaus_app.foxbit.databinding.FragmentPlayerBinding
import com.zaus_app.foxbit.view.viewmodels.PlayerViewModel


class PlayerFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private lateinit var songs: List<Song>
    private var position: Int = 0
    private var _mediaPlayer: MediaPlayer? = null
    private val mediaPlayer get() = _mediaPlayer!!
    private val viewModel: PlayerViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { layout ->
                val behaviour = BottomSheetBehavior.from(layout)
                setupFullHeight(layout)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                behaviour.skipCollapsed = true
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        songs = App.instance.songList
        position = arguments?.get("position") as Int
        createMediaPlayer()
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
        binding.nextSongButton.setOnClickListener {
            changeSong(++position)
        }
        binding.previousSongButton.setOnClickListener {
            changeSong(--position)
        }
    }

    private fun createMediaPlayer() {
        try {
            if (_mediaPlayer == null)
                _mediaPlayer = MediaPlayer()
            mediaPlayer.reset()
            mediaPlayer.setDataSource(songs[position].path)
            mediaPlayer.prepare()
        } catch (e: Exception) {
            return
        }
    }

    private fun changeSong(newPosition: Int) {
        position = when (newPosition) {
            songs.size -> 0
            -1 -> songs.size-1
            else -> newPosition
        }
        setSongDetails(songs[position])
        createMediaPlayer()
        mediaPlayer.start()
    }

    fun setSongDetails(song: Song) {
        binding.songTitle.text = song.title
        binding.albumTitle.text = song.album
    }

}
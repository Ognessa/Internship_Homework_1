package com.onix.internship.ui.player

import android.net.Uri
import android.os.Bundle
import android.view.View
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.FragmentPlayerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerFragment : BaseFragment<FragmentPlayerBinding>(R.layout.fragment_player) {

    override val viewModel: PlayerViewModel by viewModel()
    lateinit var player : ExoPlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        player = ExoPlayer.Builder(requireContext()).build()
        binding.playerView.player = player

        val mediaItem: MediaItem = MediaItem.fromUri(Uri.parse("https://youtu.be/eMoWynkRbqI"))
        player.setMediaItem(mediaItem)
        player.prepare()
    }
}
package com.astar.myapplication.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.astar.myapplication.R
import com.astar.myapplication.player.PlayerControl
import com.google.android.exoplayer2.ui.StyledPlayerView

class RadioControlFragment(stream: String) : Fragment() {

    private lateinit var playerView : StyledPlayerView
    private lateinit var play: ImageButton
    private lateinit var stop: ImageButton

    private val playerControl: PlayerControl by lazy { PlayerControl.Base(requireContext()) }
    private val media = stream // "https://rock.volna.top/RusRock"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_radio_control, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerView = view.findViewById(R.id.styled_player)
        play = view.findViewById(R.id.play)
        stop = view.findViewById(R.id.stop)
        play.setOnClickListener { playerControl.play(media) }
        stop.setOnClickListener { playerControl.stop() }
    }

    override fun onStart() {
        super.onStart()
        playerControl.attach(playerView)
        playerControl.play(media)
    }

    override fun onStop() {
        super.onStop()
        playerControl.detach()
    }
}
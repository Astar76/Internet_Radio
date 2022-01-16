package com.astar.myapplication.player

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

interface PlayerControl {

    fun attach(playerView: StyledPlayerView)

    fun detach()

    fun play(uri: String)

    fun stop()

    class Base(context: Context) : PlayerControl {

        private val player: ExoPlayer = ExoPlayer.Builder(context).build()
        private var playerView: StyledPlayerView? = null

        override fun attach(playerView: StyledPlayerView) {
            this.playerView = playerView
        }

        override fun detach() {
            if (player.isPlaying) player.stop()
            this.playerView = null
        }

        override fun play(uri: String) {
            if (playerView != null) {
                if (player.isPlaying) return
                player.setMediaItem(MediaItem.fromUri(uri))
                player.prepare()
                player.play()
            } else {
                throw IllegalStateException("PlayerControl not attached!")
            }
        }

        override fun stop() {
            if (playerView != null) {
                if (player.isPlaying) player.stop()
            } else {
                throw IllegalStateException("PlayerControl not attached!")
            }
        }
    }
}
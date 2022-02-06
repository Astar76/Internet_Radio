package com.astar.myapplication.radioplayer

import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat
import com.astar.myapplication.domain.model.Radio
import com.astar.myapplication.radioplayer.state.PlaybackStateManager
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.audio.MediaCodecAudioRenderer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory

class RadioService : Service(), Player.Listener, PlaybackStateManager.Callback {

    private lateinit var player: ExoPlayer
    private val playbackManager = PlaybackStateManager.getInstance()

    private lateinit var notification: RadioNotification
    private lateinit var notificationManager: NotificationManager

    private var isForeground = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand() called")
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        player = createPlayer()
        player.addListener(this)
        player.setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.CONTENT_TYPE_MUSIC)
                .build(),
            false
        )

        notificationManager = ContextCompat.getSystemService(this, NotificationManager::class.java)!!
        notification = RadioNotification.from(this, notificationManager)

        playbackManager.setHasPlayed(playbackManager.isPlaying)
        playbackManager.addCallback(this)

        if (playbackManager.radio != null || playbackManager.isRestored) {
            restore()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        player.release()
    }

    private fun createPlayer(): ExoPlayer {
        val audioRenderer = RenderersFactory { handler, _, audioListener, _, _ ->
            arrayOf(
                MediaCodecAudioRenderer(this, MediaCodecSelector.DEFAULT, handler, audioListener)
            )
        }

        val extractorsFactory = DefaultExtractorsFactory().setConstantBitrateSeekingEnabled(true)

        return ExoPlayer.Builder(this, audioRenderer)
            .setMediaSourceFactory(DefaultMediaSourceFactory(this, extractorsFactory))
            .setWakeMode(C.WAKE_MODE_LOCAL)
            .build()
    }

    private fun restore() {
        onPlayingUpdate(playbackManager.isPlaying)
        onRadioStationUpdate(playbackManager.radio)
    }

    // Start - Playback State Manager

    override fun onRadioStationUpdate(radio: Radio?) {
        if (radio != null) {
            player.setMediaItem(MediaItem.fromUri(radio.stream))
            player.prepare()
            return
        }
        player.stop()
    }

    override fun onPlayingUpdate(isPlaying: Boolean) {
        if (isPlaying && !player.isPlaying) {
            player.play()
        } else {
            player.pause()
        }

        notification.setPlaying(isPlaying)
        startForegroundOrNotify()
    }

    private fun startForegroundOrNotify() {
        if (playbackManager.hasPlayed && playbackManager.radio != null) {
            if (!isForeground) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    startForeground(
                        RadioNotification.NOTIFICATION_ID, notification.build(),
                        ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
                    )
                } else {
                    startForeground(RadioNotification.NOTIFICATION_ID, notification.build())
                }
            }

            isForeground = true
        } else {
            notificationManager.notify(
                RadioNotification.NOTIFICATION_ID, notification.build()
            )
        }
    }

    // End - Playback State Manager

    private inner class SystemEventReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                ACTION_PLAY_PAUSE -> {}
                ACTION_EXIT -> {}
            }
        }
    }

    companion object {
        private const val TAG = "RadioService"

        const val ACTION_PLAY_PAUSE = "action.play_pause"
        const val ACTION_EXIT = "action.exit"
    }
}
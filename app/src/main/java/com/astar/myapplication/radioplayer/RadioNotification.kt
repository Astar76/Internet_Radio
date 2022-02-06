package com.astar.myapplication.radioplayer

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.astar.myapplication.R
import com.astar.myapplication.newBroadcastIntent


@SuppressLint("RestrictedApi")
class RadioNotification private constructor(
    private val context: Context
) : NotificationCompat.Builder(context, CHANNEL_ID) {

    init {
        setSmallIcon(R.drawable.icon_play)
        setCategory(NotificationCompat.CATEGORY_SERVICE)
        setShowWhen(false)
        setSilent(true)
        setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

    }

    fun setMetadata(urlStream: String) {
        setContentTitle(urlStream)
        setContentText("Играем...")
    }

    fun setPlaying(isPlaying: Boolean) {
//        mActions[2] = buildPlayPauseAction(context, isPlaying)
    }


    private fun buildPlayPauseAction(
        context: Context,
        isPlaying: Boolean
    ): NotificationCompat.Action {
        val drawableRes = if (isPlaying) R.drawable.icon_stop else R.drawable.icon_play
        return buildAction(context, RadioService.ACTION_PLAY_PAUSE, drawableRes)
    }

    private fun buildAction(
        context: Context,
        actionName: String,
        @DrawableRes iconRes: Int
    ): NotificationCompat.Action {
        val action  = NotificationCompat.Action.Builder(
            iconRes, actionName,
            context.newBroadcastIntent(actionName)
        )
        return action.build()
    }


    companion object {
        const val CHANNEL_ID = "channel_radio_playback"
        const val NOTIFICATION_ID = 1222

        fun from(
            context: Context,
            notificationManager: NotificationManager
        ): RadioNotification {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID, "Internet Radio",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }
            return RadioNotification(context)
        }
    }
}
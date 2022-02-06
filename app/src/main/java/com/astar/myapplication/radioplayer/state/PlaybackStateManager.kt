package com.astar.myapplication.radioplayer.state

import android.util.Log
import com.astar.myapplication.domain.model.Radio

class PlaybackStateManager private constructor() {

    private val callbacks = mutableListOf<Callback>()

    private var mRadio: Radio? = null
        set(value) {
            field = value
            callbacks.forEach { it.onRadioStationUpdate(value) }
        }

    private var mIsPlaying = false
        set(value) {
            field = value
            callbacks.forEach { it.onPlayingUpdate(value) }
            Log.d(TAG, "mIsPlaying: $value ")
        }

    private var mHasPlayed = false
    private var mIsRestored = false

    val radio: Radio? get() = mRadio
    val isPlaying: Boolean get() = mIsPlaying
    val isRestored: Boolean get() = mIsRestored
    val hasPlayed: Boolean get() = mHasPlayed

    fun addCallback(callback: Callback) {
        callbacks.add(callback)
    }

    fun removeCallback(callback: Callback) {
        callbacks.remove(callback)
    }

    fun markRestored() {
        mIsRestored = true
    }

    fun playRadio(radio: Radio) {
        Log.d(TAG, "playRadio() updating stream to $radio")
        this.mRadio = radio
    }

    fun setPlaying(playing: Boolean) {
        if (mIsPlaying != playing) {
            if (playing) {
                mHasPlayed = true
            }
            mIsPlaying = playing
        }
    }

    fun setHasPlayed(hasPlayed: Boolean) {
        mHasPlayed = hasPlayed
    }

    interface Callback {
        fun onRadioStationUpdate(radio: Radio?)
        fun onPlayingUpdate(isPlaying: Boolean)
    }

    companion object {

        const val TAG = "PlaybackStateManager"

        @Volatile
        private var INSTANCE: PlaybackStateManager? = null

        fun getInstance(): PlaybackStateManager {
            val instance = INSTANCE

            if (instance != null) {
                return instance
            }

            synchronized(this) {
                val newInstance = PlaybackStateManager()
                INSTANCE = newInstance
                return newInstance
            }
        }
    }
}
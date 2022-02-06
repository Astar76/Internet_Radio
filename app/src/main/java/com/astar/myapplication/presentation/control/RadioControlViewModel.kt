package com.astar.myapplication.presentation.control

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.astar.myapplication.domain.model.Radio
import com.astar.myapplication.radioplayer.state.PlaybackStateManager

class RadioControlViewModel: ViewModel(), PlaybackStateManager.Callback {

    private val mIsPlaying = MutableLiveData(false)
    private val mRadioStream = MutableLiveData<Radio?>()

    private val playbackManager = PlaybackStateManager.getInstance()

    init {
        playbackManager.addCallback(this)
        if (playbackManager.isRestored) {
            restorePlaybackState()
        }
    }

    fun observeIsPlaying(owner: LifecycleOwner, observer: Observer<Boolean>) {
        mIsPlaying.observe(owner, observer)
    }

    fun playRadio(streamUrl: Radio) {
        playbackManager.playRadio(streamUrl)
    }

    fun changePlayingStatus() {
        playbackManager.setPlaying(!playbackManager.isPlaying)
    }

    private fun restorePlaybackState() {
        mRadioStream.value = playbackManager.radio
        mIsPlaying.value = playbackManager.isPlaying
    }

    // region - playback State Manager Callback

    override fun onRadioStationUpdate(radio: Radio?) {
        mRadioStream.value = radio
    }

    override fun onPlayingUpdate(isPlaying: Boolean) {
        mIsPlaying.value = isPlaying
    }

    // endregion - playback state manager callback

    override fun onCleared() {
        playbackManager.removeCallback(this)
    }
}
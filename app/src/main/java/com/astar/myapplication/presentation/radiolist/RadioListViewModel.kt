package com.astar.myapplication.presentation.radiolist

import androidx.lifecycle.*
import com.astar.myapplication.core.Result
import com.astar.myapplication.domain.RadioRepository
import com.astar.myapplication.domain.model.Radio
import com.astar.myapplication.radioplayer.state.PlaybackStateManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RadioListViewModel(
    private val repository: RadioRepository
) : ViewModel(), PlaybackStateManager.Callback {

    private val radioListLiveData = MutableLiveData<RadioResult>()
    private val playingState = MutableLiveData(false)

    private val playbackManager = PlaybackStateManager.getInstance()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            radioListLiveData.postValue(RadioResult.Loading)
            when (val result = repository.fetchAll()) {
                is Result.Success -> radioListLiveData.postValue(RadioResult.Success(result.data))
                is Result.Error -> radioListLiveData.postValue(RadioResult.Error(result.ex.message!!))
            }
        }
        playbackManager.addCallback(this)
        playingState.value = playbackManager.isPlaying
    }

    fun observeRadioList(owner: LifecycleOwner, observer: Observer<RadioResult>) {
        radioListLiveData.observe(owner, observer)
    }

    fun observePlayingState(owner: LifecycleOwner, observer: Observer<Boolean>) {
        playingState.observe(owner, observer)
    }

    fun playRadio(radio: Radio) {
        playbackManager.playRadio(radio)
        playbackManager.setPlaying(true)
    }

    fun changePlayingState() {
        playbackManager.setPlaying(!playbackManager.isPlaying)
    }

    override fun onRadioStationUpdate(radio: Radio?) {

    }

    override fun onPlayingUpdate(isPlaying: Boolean) {
        playingState.value = isPlaying
    }

    override fun onCleared() {
        playbackManager.removeCallback(this)
        super.onCleared()
    }

}
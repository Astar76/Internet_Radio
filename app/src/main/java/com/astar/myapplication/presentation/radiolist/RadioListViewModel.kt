package com.astar.myapplication.presentation.radiolist

import androidx.lifecycle.*
import com.astar.myapplication.core.Result
import com.astar.myapplication.domain.RadioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RadioListViewModel(
    private val repository: RadioRepository
) : ViewModel() {

    private val radioListLiveData = MutableLiveData<RadioResult>()

    init {
        loadData()
    }

    fun observe(owner: LifecycleOwner, observer: Observer<RadioResult>) {
        radioListLiveData.observe(owner, observer)
    }

    private fun loadData() = viewModelScope.launch(Dispatchers.IO) {
        radioListLiveData.postValue(RadioResult.Loading)
        when (val result = repository.fetchAll()) {
            is Result.Success -> radioListLiveData.postValue(RadioResult.Success(result.data))
            is Result.Error -> radioListLiveData.postValue(RadioResult.Error(result.ex.message!!))
        }
    }
}
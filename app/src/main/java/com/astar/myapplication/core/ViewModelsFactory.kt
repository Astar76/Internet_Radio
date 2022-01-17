package com.astar.myapplication.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.astar.myapplication.presentation.radiolist.RadioListViewModel
import com.astar.myapplication.servicelocator.DependencyContainer
import com.astar.myapplication.servicelocator.Feature
import java.lang.IllegalStateException

class ViewModelsFactory(
    private val dependencyContainer: DependencyContainer
) : ViewModelProvider.Factory {

    private val map = HashMap<Class<*>, Feature>().apply {
        put(RadioListViewModel::class.java, Feature.RADIO_LIST)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val feature = map[modelClass] ?: throw IllegalStateException("ViewModel not found $modelClass")
        return dependencyContainer.module(feature).viewModel() as T
    }
}
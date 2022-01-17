package com.astar.myapplication.servicelocator

import android.app.Application
import androidx.lifecycle.*
import com.astar.myapplication.core.ViewModelsFactory

class RadioApp : Application() {

    private lateinit var coreModule: CoreModule

    private val factory by lazy {
        ViewModelsFactory(DependencyContainer.Base(coreModule))
    }

    override fun onCreate() {
        super.onCreate()
        coreModule = CoreModule.Base(this)
    }

    fun <T: ViewModel> viewModel(modelClass: Class<T>, owner: ViewModelStoreOwner): T {
        return ViewModelProvider(owner, factory)[modelClass]
    }
}
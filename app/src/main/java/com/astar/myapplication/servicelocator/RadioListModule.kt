package com.astar.myapplication.servicelocator

import com.astar.myapplication.data.BaseRadioRepository
import com.astar.myapplication.data.local.RadioLocalDataSource
import com.astar.myapplication.data.remote.RadioRemoteDataSource
import com.astar.myapplication.presentation.radiolist.RadioListViewModel

class RadioListModule(
    private val coreModule: CoreModule
) : BaseModule<RadioListViewModel> {

    override fun viewModel() = RadioListViewModel(
        BaseRadioRepository(
            RadioRemoteDataSource.Base(coreModule.provideRadioService()),
            RadioLocalDataSource.Base(coreModule.provideDatabase().radioDao())
        )
    )
}
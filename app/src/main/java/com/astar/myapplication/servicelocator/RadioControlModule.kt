/*
 * Created by Astar (c) on 2022.
 */

package com.astar.myapplication.servicelocator

import android.util.Log
import com.astar.myapplication.presentation.control.RadioControlViewModel

class RadioControlModule(private val coreModule: CoreModule) : BaseModule<RadioControlViewModel> {
    override fun viewModel(): RadioControlViewModel {
        return RadioControlViewModel()
    }
}
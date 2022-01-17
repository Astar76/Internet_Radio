package com.astar.myapplication.servicelocator

import androidx.lifecycle.ViewModel

interface BaseModule<T : ViewModel> {

    fun viewModel(): T
}
package com.astar.myapplication.presentation.radiolist

import com.astar.myapplication.domain.model.Radio

sealed class RadioResult {
    object Loading: RadioResult()
    data class Success(val data: List<Radio>): RadioResult()
    data class Error(val message: String): RadioResult()
}
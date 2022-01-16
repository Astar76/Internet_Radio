package com.astar.myapplication.domain

import com.astar.myapplication.core.Result
import com.astar.myapplication.domain.model.Radio

interface RadioRepository {

    suspend fun fetchAll(): Result<List<Radio>, Throwable>
}
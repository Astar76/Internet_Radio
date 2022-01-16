package com.astar.myapplication.data.remote

import com.astar.myapplication.data.remote.model.Radios
import com.astar.myapplication.data.remote.service.RadioServiceApi

interface RadioRemoteDataSource {

    suspend fun fetchAll(): Radios

    class Base(private val service: RadioServiceApi) : RadioRemoteDataSource {
        override suspend fun fetchAll(): Radios {
            return service.fetchPopularByCountry()
        }
    }
}
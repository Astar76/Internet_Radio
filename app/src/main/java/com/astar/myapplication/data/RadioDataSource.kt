package com.astar.myapplication.data

import com.astar.myapplication.data.remote.RadioServiceApi

interface RadioDataSource {

    suspend fun fetchPopular(): List<RadioData>

    class Base(private val serviceApi: RadioServiceApi): RadioDataSource {

        override suspend fun fetchPopular(): List<RadioData> {
            return serviceApi.fetchPopularByCountry().toRadioData()
        }
    }
}
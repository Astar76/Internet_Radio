package com.astar.myapplication.data.remote

import retrofit2.http.GET

interface RadioServiceApi {

    @GET("radios/getTopByCountry?query=ru")
    suspend fun fetchPopularByCountry() : Radios
}
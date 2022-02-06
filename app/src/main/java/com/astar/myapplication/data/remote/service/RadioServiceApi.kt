package com.astar.myapplication.data.remote.service

import com.astar.myapplication.data.remote.model.Radios
import retrofit2.http.GET

interface RadioServiceApi {

    // @GET("radios/getTopByCountry?query=ru")
    // suspend fun fetchPopularByCountry() : Radios

    @GET("/radios/")
    suspend fun fetchPopularByCountry() : Radios

}
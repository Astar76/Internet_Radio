package com.astar.myapplication.core

import android.app.Application
import android.util.Log
import com.astar.myapplication.data.remote.Radios
import com.astar.myapplication.data.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RadioApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
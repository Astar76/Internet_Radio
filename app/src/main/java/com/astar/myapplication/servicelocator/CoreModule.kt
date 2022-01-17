package com.astar.myapplication.servicelocator

import android.content.Context
import com.astar.myapplication.data.local.AppDatabase
import com.astar.myapplication.data.remote.RetrofitClient
import com.astar.myapplication.data.remote.service.RadioServiceApi

interface CoreModule {

    fun provideDatabase(): AppDatabase

    fun provideRadioService(): RadioServiceApi

    class Base(private val context: Context) : CoreModule {

        override fun provideDatabase(): AppDatabase = AppDatabase.getInstance(context)

        override fun provideRadioService(): RadioServiceApi = RetrofitClient.create()
    }
}
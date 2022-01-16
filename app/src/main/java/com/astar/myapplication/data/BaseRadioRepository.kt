package com.astar.myapplication.data

import android.util.Log
import com.astar.myapplication.core.Result
import com.astar.myapplication.data.local.RadioLocalDataSource
import com.astar.myapplication.data.remote.RadioRemoteDataSource
import com.astar.myapplication.domain.RadioRepository
import com.astar.myapplication.domain.model.Radio

class BaseRadioRepository(
    private val remoteDataSource: RadioRemoteDataSource,
    private val localDataSource: RadioLocalDataSource,
) : RadioRepository {

    override suspend fun fetchAll(): Result<List<Radio>, Throwable> {
        return try {
            val cachedList = localDataSource.fetchAll().map { it.toRadio() }
            if (cachedList.isEmpty()) {
                val remoteList = remoteDataSource.fetchAll().toRadioList()
                val list = remoteList.map { it.toRadioEntity() }
                localDataSource.save(list)
                Log.d("Repository", "fetchAll() remote source")
                Result.Success(remoteList)
            } else {
                Log.d("Repository", "fetchAll() cache source")
                Result.Success(cachedList)
            }
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }
}
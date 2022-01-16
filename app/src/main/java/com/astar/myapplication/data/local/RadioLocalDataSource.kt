package com.astar.myapplication.data.local

import com.astar.myapplication.data.local.model.RadioEntity

interface RadioLocalDataSource {

    suspend fun fetchAll(): List<RadioEntity>

    suspend fun save(radioList: List<RadioEntity>)

    suspend fun clear()

    class Base(
        private val dao: RadioDao
    ): RadioLocalDataSource {
        override suspend fun fetchAll(): List<RadioEntity> = dao.fetchAll()

        override suspend fun save(radioList: List<RadioEntity>) = dao.insertRadio(radioList)

        override suspend fun clear() = dao.clearAll()
    }
}
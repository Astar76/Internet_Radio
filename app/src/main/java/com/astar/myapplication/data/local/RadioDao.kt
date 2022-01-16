package com.astar.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.astar.myapplication.data.local.model.RadioEntity

@Dao
interface RadioDao {

    @Query("SELECT * FROM radio")
    suspend fun fetchAll(): List<RadioEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRadio(radio: List<RadioEntity>)

    @Query("DELETE FROM radio")
    suspend fun clearAll()
}
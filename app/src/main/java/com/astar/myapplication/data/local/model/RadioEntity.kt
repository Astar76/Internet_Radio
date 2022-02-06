package com.astar.myapplication.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "radio")
data class RadioEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val channelId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "stream")
    val streamUrl: String,
    @ColumnInfo(name = "image")
    val imageUrl: String
)
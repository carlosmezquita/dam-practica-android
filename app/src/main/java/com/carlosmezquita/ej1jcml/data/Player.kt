package com.carlosmezquita.ej1jcml.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class Player(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Double?,
    @ColumnInfo(name = "position") val position: PlayerPositions?,
    @ColumnInfo(name = "score") val score: Int?
) : java.io.Serializable

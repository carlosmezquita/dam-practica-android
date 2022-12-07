package com.carlosmezquita.ej1jcml.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player")
    fun getAll(): List<Player>

    @Query("SELECT * FROM player WHERE id IN (:playerIds)")
    fun loadAllByIds(playerIds: IntArray): List<Player>

    @Query("SELECT * FROM player WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Player

    @Insert
    fun insertAll(vararg players: Player)

    @Delete
    fun delete(player: Player)
}
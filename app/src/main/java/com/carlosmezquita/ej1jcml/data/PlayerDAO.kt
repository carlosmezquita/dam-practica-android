package com.carlosmezquita.ej1jcml.data

import androidx.room.*

@Dao
interface PlayerDAO {
    @Query("SELECT * FROM player")
    fun getAll(): List<Player>

    @Query("SELECT * FROM player WHERE id IN (:playerIds)")
    fun loadAllByIds(playerIds: IntArray): List<Player>

    @Query("SELECT * FROM player WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Player

    @Query("SELECT * FROM player WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): Player

    @Update
    fun updatePlayer(player: Player)

    @Insert
    fun insertAll(vararg players: Player)

    @Delete
    fun delete(player: Player)
}
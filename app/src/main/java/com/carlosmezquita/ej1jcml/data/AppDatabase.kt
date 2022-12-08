package com.carlosmezquita.ej1jcml.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Player::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDAO

}
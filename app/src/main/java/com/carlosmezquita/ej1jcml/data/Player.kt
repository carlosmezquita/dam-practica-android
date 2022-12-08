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
) {
    companion object {
        val data
            get() = listOf(
                Player(
                    1,
                    "Messi",
                    9999.43,
                    PlayerPositions.STRIKER,
                    99
                ),
                Player(
                    2,
                    "CR7",
                    9899.43,
                    PlayerPositions.STRIKER,
                    95
                ),
                Player(
                    3,
                    "Casillas",
                    9999.43,
                    PlayerPositions.GOALKEEPER,
                    70
                ),
                Player(
                    4,
                    "Maradona",
                    9999.43,
                    PlayerPositions.STRIKER,
                    99
                ),
                Player(
                    5,
                    "Iniesta",
                    9899.43,
                    PlayerPositions.STRIKER,
                    95
                ),
                Player(
                    6,
                    "Unai",
                    9999.43,
                    PlayerPositions.GOALKEEPER,
                    70
                ), Player(
                    7,
                    "Villa",
                    9999.43,
                    PlayerPositions.STRIKER,
                    99
                )
            )
    }
}

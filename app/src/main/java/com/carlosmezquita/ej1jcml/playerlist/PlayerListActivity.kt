package com.carlosmezquita.ej1jcml.playerlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.carlosmezquita.ej1jcml.AddPlayerActivity
import com.carlosmezquita.ej1jcml.R
import com.carlosmezquita.ej1jcml.data.AppDatabase

class PlayerListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.players_list_activity)
        title = "Jugadores disponibles";
        val playerList: RecyclerView = findViewById(R.id.list) // (1)

        val playerAdapter = PlayerListAdapter() // (2)
        playerList.adapter = playerAdapter // (3)

//        playerAdapter.players = Player.data // (4)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "players-database"
        ).allowMainThreadQueries().build()
        val playerDao = db.playerDao()

        playerAdapter.players = playerDao.getAll()

        val add: View = findViewById(R.id.add_player)
        add.setOnClickListener {
            val intent = Intent(this@PlayerListActivity, AddPlayerActivity::class.java)
            startActivity(intent)
        }
    }
}
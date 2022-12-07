package com.carlosmezquita.ej1jcml.playerlist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.carlosmezquita.ej1jcml.R
import com.carlosmezquita.ej1jcml.data.Player
import com.google.android.material.snackbar.Snackbar

class PlayerListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.players_list_activity)
        title = "Jugadores disponibles";
        val playerList: RecyclerView = findViewById(R.id.list) // (1)

        val playerAdapter = PlayerListAdapter() // (2)
        playerList.adapter = playerAdapter // (3)

        playerAdapter.players = Player.data // (4)

        val add: View = findViewById(R.id.add_player)
        add.setOnClickListener { view ->
            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
    }
}
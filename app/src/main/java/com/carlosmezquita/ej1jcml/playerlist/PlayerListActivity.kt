package com.carlosmezquita.ej1jcml.playerlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.carlosmezquita.ej1jcml.AddPlayerActivity
import com.carlosmezquita.ej1jcml.EditPlayerActivity
import com.carlosmezquita.ej1jcml.R
import com.carlosmezquita.ej1jcml.data.AppDatabase
import com.carlosmezquita.ej1jcml.data.Player
import com.carlosmezquita.ej1jcml.databinding.PlayersListActivityBinding


class PlayerListActivity : AppCompatActivity() {
    lateinit var binding: PlayersListActivityBinding

    companion object {
        lateinit var data: MutableList<Player>
        lateinit var adapter: PlayerListAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PlayersListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Jugadores"

        val db: AppDatabase by lazy {
            Room.databaseBuilder(
                application,
                AppDatabase::class.java, "players-database"
            ).allowMainThreadQueries().build()
        }
        val playerDao = db.playerDao()

        data = playerDao.getAll() as MutableList<Player>

        adapter = PlayerListAdapter(data) { player -> onItemClick(player.id) }


        // (2)
        binding.list.adapter = adapter

        if (adapter.itemCount == 0) {
            binding.playersAmount.text = getString(R.string.no_players)
        } else if (adapter.itemCount == 1) {
            binding.playersAmount.text = "Hay ${adapter.itemCount} jugador"
        } else {
            binding.playersAmount.text = "Hay ${adapter.itemCount} jugadores"
        }


        binding.addPlayer.setOnClickListener {
            val intent = Intent(this@PlayerListActivity, AddPlayerActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if (adapter.itemCount == 0) {
            binding.playersAmount.text = getString(R.string.no_players)
        } else if (adapter.itemCount == 1) {
            binding.playersAmount.text = "Hay ${adapter.itemCount} jugador"
        } else {
            binding.playersAmount.text = "Hay ${adapter.itemCount} jugadores"
        }
    }

    fun insertItem(player: Player) {
        data.add(0, player)
        adapter.notifyItemInserted(0)
    }

    fun updateItem(player: Player) {
        val index = getPlayerById(player)
        data[index] = player
        adapter.notifyItemChanged(index)
    }

    fun removeItem(player: Player) {
        val index = getPlayerById(player)
        data.removeAt(index)
        adapter.notifyItemRemoved(index)
    }

    private fun onItemClick(id: Int) {
        val intent = Intent(this@PlayerListActivity, EditPlayerActivity::class.java)
        intent.putExtra("PLAYER_SELECTED", id)
        startActivity(intent)
    }

    private fun getPlayerById(player: Player): Int {
        val playerEdited = data.find { it.id == player.id }
        val index = data.indexOf(playerEdited)
        Log.v("INDEX", index.toString())
        return index
    }

}
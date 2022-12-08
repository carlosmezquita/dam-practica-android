package com.carlosmezquita.ej1jcml

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.carlosmezquita.ej1jcml.data.AppDatabase
import com.carlosmezquita.ej1jcml.data.Player
import com.carlosmezquita.ej1jcml.databinding.AddPlayerActivityBinding
import com.carlosmezquita.ej1jcml.playerlist.PlayerListActivity


class AddPlayerActivity : AppCompatActivity() {
    private lateinit var binding: AddPlayerActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddPlayerActivityBinding.inflate(layoutInflater)
        val view = binding.root
        title = "Añada un jugador";
        setContentView(view)
        binding.submitButton.setOnClickListener { submitPlayer() }
        binding.cancelButton.setOnClickListener { backToPlayerList() }
    }

    private fun backToPlayerList() {
        toPlayerList()
    }

    private fun submitPlayer() {
        val playerCode = binding.playerCode.text.toString().toInt()
        val playerName = binding.playerName.text.toString()
        val playerPrice = binding.playerPrice.text.toString().toDoubleOrNull()
        val playerScore = binding.playerScore.text.toString().toIntOrNull()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "players-database"
        ).allowMainThreadQueries().build()
        val playerDao = db.playerDao()

        playerDao.insertAll(Player(playerCode, playerName, playerPrice, null, playerScore))
        toPlayerList()
    }

    private fun toPlayerList() {
        val intent = Intent(this@AddPlayerActivity, PlayerListActivity::class.java)
        startActivity(intent)
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                0
            )
        }
    }
}
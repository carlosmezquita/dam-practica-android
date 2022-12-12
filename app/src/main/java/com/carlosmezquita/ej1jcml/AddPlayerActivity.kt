package com.carlosmezquita.ej1jcml

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.room.Room
import com.carlosmezquita.ej1jcml.data.AppDatabase
import com.carlosmezquita.ej1jcml.data.Player
import com.carlosmezquita.ej1jcml.data.PlayerPositions
import com.carlosmezquita.ej1jcml.databinding.EditPlayerActivityBinding
import com.carlosmezquita.ej1jcml.playerlist.PlayerListActivity


class AddPlayerActivity : AppCompatActivity() {
    private lateinit var binding: EditPlayerActivityBinding
    private lateinit var selectorOptions : Spinner
    private var playerPosition: PlayerPositions? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditPlayerActivityBinding.inflate(layoutInflater)
        val view = binding.root
        title = "Añada un jugador"
        setContentView(view)
        binding.submitButton.setOnClickListener { showDialog() }
        binding.cancelButton.setOnClickListener {this@AddPlayerActivity.onBackPressedDispatcher.onBackPressed() }

        selectorOptions = binding.positionSelector

        val positionValues =
            ArrayAdapter.createFromResource(this, R.array.positions_array, android.R.layout.simple_spinner_item)

        positionValues.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectorOptions.adapter = positionValues


        selectorOptions.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.v("POSITION", "nothing selected")
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                val pos = parent.getItemAtPosition(position)
                playerPosition = when (position) {
                    0 -> PlayerPositions.GOALKEEPER
                    1 -> PlayerPositions.DEFENDER
                    2 -> PlayerPositions.MIDFIELDER
                    3 -> PlayerPositions.STRIKER
                    else -> {
                        null
                    }
                }
            }
        }
    }


    private fun submitPlayer() {
        Utils().hideSoftKeyboard(this)
        val playerCode = binding.playerCode.text.toString().toInt()
        val playerName = binding.playerName.text.toString()
        val playerPrice = binding.playerPrice.text.toString().toDoubleOrNull()
        val playerScore = binding.playerScore.text.toString().toIntOrNull()

        val player = Player(playerCode, playerName, playerPrice, playerPosition, playerScore)
        Log.v("PLAYER", player.toString())
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "players-database"
        ).allowMainThreadQueries().build()
        try {
            db.playerDao().insertAll(player)
        }catch (e: SQLiteConstraintException){
            binding.playerCode.error = "Ya existe un jugador con este código"
            return
        }
        PlayerListActivity().insertItem(player)
        super.finish()
    }

    private fun showDialog() {
        val valid: Boolean = Utils().checkParameters(binding)
        if (!valid){return}
        this@AddPlayerActivity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("AÑADIR",
                    DialogInterface.OnClickListener { dialog, id ->
                        submitPlayer()
                        dialog.cancel()
                    })
                setNegativeButton("CANCELAR",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
                setTitle("Añadir jugador")
                setMessage("¿Estás seguro de que deseas añadir al jugador?")
            }
            // Set other dialog properties
            // Create the AlertDialog
            builder.create()
        }?.show()
    }


    }

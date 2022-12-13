package com.carlosmezquita.ej1jcml

import android.app.AlertDialog
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.room.Room
import com.carlosmezquita.ej1jcml.data.AppDatabase
import com.carlosmezquita.ej1jcml.data.Player
import com.carlosmezquita.ej1jcml.data.PlayerPositions
import com.carlosmezquita.ej1jcml.databinding.EditPlayerActivityBinding


class AddPlayerActivity : AppCompatActivity() {
    private lateinit var binding: EditPlayerActivityBinding
    private var playerPosition: PlayerPositions? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditPlayerActivityBinding.inflate(layoutInflater)
        val view = binding.root
        title = "Añada un jugador"
        setContentView(view)
        binding.submitButton.setOnClickListener { showDialog() }
        binding.playerCode.isVisible = false

        binding.toolbar.navigationIcon =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_arrow_back_24, theme)
        binding.toolbar.setNavigationOnClickListener {
            this@AddPlayerActivity.onBackPressedDispatcher.onBackPressed()
        }

        val positions = resources.getStringArray(R.array.positions_array)
        val arrayAdapter = ArrayAdapter(this@AddPlayerActivity, R.layout.dropdown_item, positions)
        binding.spinnerPosition.setAdapter(arrayAdapter)

        binding.spinnerPosition.onItemClickListener =
            OnItemClickListener { _, _, position, _ ->
                binding.spinnerLayout.error = ""
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

    override fun onResume() {
        super.onResume()
        val positions = resources.getStringArray(R.array.positions_array)
        val arrayAdapter = ArrayAdapter(this@AddPlayerActivity, R.layout.dropdown_item, positions)
        binding.spinnerPosition.setAdapter(arrayAdapter)
        binding.spinnerPosition.setAdapter(arrayAdapter)
    }

    private fun submitPlayer() {
        Utils().hideSoftKeyboard(this)
//        val playerCode = binding.playerCode.text.toString().toInt()
        val playerName = binding.playerName.text.toString()
//        val playerPos = binding.spinnerPosition.get
        val playerPrice = binding.playerPrice.text.toString().toDoubleOrNull()
        val playerScore = binding.playerScore.text.toString().toIntOrNull()

        val player = Player(0, playerName, playerPrice, playerPosition, playerScore)
        Log.v("PLAYER", player.toString())
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "players-database"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
        try {
            db.playerDao().insertAll(player)
        } catch (e: SQLiteConstraintException) {
            binding.playerCode.error = "Ya existe un jugador con este código"
            return
        }
//        PlayerListActivity().insertItem(player)
        super.finish()
    }

    private fun showDialog() {
        val valid: Boolean = Utils().checkParameters(binding)
        if (!valid) {
            return
        }
        this@AddPlayerActivity.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(
                    "AÑADIR"
                ) { dialog, _ ->
                    submitPlayer()
                    dialog.cancel()
                }
                setNegativeButton(
                    "CANCELAR"
                ) { dialog, _ ->
                    dialog.cancel()
                }
                setTitle("Añadir jugador")
                setMessage("¿Estás seguro de que deseas añadir al jugador?")
            }
            // Set other dialog properties
            // Create the AlertDialog
            builder.create()
        }?.show()
    }


}

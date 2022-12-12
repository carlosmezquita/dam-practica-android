package com.carlosmezquita.ej1jcml

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.room.Room
import com.carlosmezquita.ej1jcml.data.AppDatabase
import com.carlosmezquita.ej1jcml.data.Player
import com.carlosmezquita.ej1jcml.data.PlayerPositions
import com.carlosmezquita.ej1jcml.databinding.EditPlayerActivityBinding
import com.carlosmezquita.ej1jcml.playerlist.PlayerListActivity


class EditPlayerActivity : AppCompatActivity() {
    private lateinit var binding: EditPlayerActivityBinding
    private lateinit var selectorOptions: Spinner
    private var playerPosition: PlayerPositions? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditPlayerActivityBinding.inflate(layoutInflater)
        val view = binding.root
        title = "Editar jugador"
        setContentView(view)

        val playerId: Int = intent.getIntExtra("PLAYER_SELECTED", -1)

        val db: AppDatabase by lazy {
            Room.databaseBuilder(
                application,
                AppDatabase::class.java, "players-database"
            ).allowMainThreadQueries().build()
        }
        val playerDao = db.playerDao()

        val player = playerDao.findById(playerId)

        binding.deleteButton.isVisible = true

        binding.playerCode.setText(player.id.toString())
        binding.playerCode.isEnabled = false
        binding.playerName.setText(player.name)
        binding.playerPrice.setText(player.price.toString())
        binding.playerScore.setText(player.score.toString())

        selectorOptions = binding.positionSelector

        val positionValues =
            ArrayAdapter.createFromResource(
                this,
                R.array.positions_array,
                android.R.layout.simple_spinner_item
            )

        positionValues.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectorOptions.adapter = positionValues

        val index = when (player.position) {
            PlayerPositions.GOALKEEPER -> 0
            PlayerPositions.DEFENDER -> 1
            PlayerPositions.MIDFIELDER -> 2
            // else = striker
            else -> {
                3
            }
        }

        selectorOptions.setSelection(index)

        selectorOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.v("POSITION", "nothing selected")
            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {

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

        binding.submitButton.text = resources.getString(R.string.edit)
        binding.deleteButton.setOnClickListener { showDialogDelete(player) }
        binding.submitButton.setOnClickListener { showDialogSubmit() }
        binding.cancelButton.setOnClickListener { this@EditPlayerActivity.onBackPressedDispatcher.onBackPressed() }


    }

    private fun submitPlayer() {
        Utils().hideSoftKeyboard(this)
        val playerCode = binding.playerCode.text.toString().toInt()
        val playerName = binding.playerName.text.toString()
        val playerPrice = binding.playerPrice.text.toString().toDoubleOrNull()
        val playerScore = binding.playerScore.text.toString().toIntOrNull()

        val player = Player(playerCode, playerName, playerPrice, playerPosition, playerScore)
        val db = getDatabase()
        db.playerDao().updatePlayer(player)
        PlayerListActivity().updateItem(player)
        super.finish()
    }

    private fun deletePlayer(player: Player) {
        val db = getDatabase()
        db.playerDao().delete(player)
        PlayerListActivity().removeItem(player)
        super.finish()
    }

    private fun getDatabase(): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "players-database"
        ).allowMainThreadQueries().build()
    }

    private fun showDialogSubmit() {
        val valid: Boolean = Utils().checkParameters(binding)
        if (!valid) {
            return
        }
        this@EditPlayerActivity.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("APLICAR"
                ) { dialog, _ ->
                    submitPlayer()
                    dialog.cancel()
                }
                setNegativeButton("CANCELAR"
                ) { dialog, _ ->
                    dialog.cancel()
                }
                setTitle("Aplicar cambios")
                setMessage("¿Estás seguro de que deseas aplicar los cambios jugador?")
            }
            // Set other dialog properties
            // Create the AlertDialog
            builder.create()
        }?.show()
    }

    private fun showDialogDelete(player: Player) {
        val valid: Boolean = Utils().checkParameters(binding)
        if (!valid) {
            return
        }
        this@EditPlayerActivity.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("ELIMINAR",
                    DialogInterface.OnClickListener { dialog, _ ->
                        deletePlayer(player)
                        dialog.cancel()
                    })
                setNegativeButton("CANCELAR",
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
                setTitle("Eliminar jugador")
                setMessage("¿Estás seguro de que deseas eliminar el jugador?")
            }
            // Set other dialog properties
            // Create the AlertDialog
            builder.create()
        }?.show()
    }

}
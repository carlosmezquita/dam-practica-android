package com.carlosmezquita.ej1jcml

import android.app.AlertDialog
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.room.Room
import com.carlosmezquita.ej1jcml.data.AppDatabase
import com.carlosmezquita.ej1jcml.data.Player
import com.carlosmezquita.ej1jcml.data.PlayerPositions
import com.carlosmezquita.ej1jcml.databinding.EditPlayerActivityBinding


class EditPlayerActivity : AppCompatActivity() {
    private lateinit var binding: EditPlayerActivityBinding
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
            ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
        }
        val playerDao = db.playerDao()

        val player = playerDao.findById(playerId)

        println(player)
        binding.playerCode.isVisible
        binding.playerCode.setText(player.id.toString())
        binding.playerCode.isEnabled = false
        binding.playerName.setText(player.name)
        binding.playerPrice.setText(player.price.toString())
        binding.playerScore.setText(player.score.toString())


        val posString = when (player.position) {
            PlayerPositions.GOALKEEPER -> "Portero (PO)"
            PlayerPositions.DEFENDER -> "Defensa (D)"
            PlayerPositions.MIDFIELDER -> "Mediocampista (MC)"
            PlayerPositions.STRIKER -> "Delantero (DE)"
            else -> {
                "Sin asignar"
            }
        }
        binding.spinnerPosition.setText(posString)


        val positions = resources.getStringArray(R.array.positions_array)
        val arrayAdapter = ArrayAdapter(this@EditPlayerActivity, R.layout.dropdown_item, positions)
        binding.spinnerPosition.setAdapter(arrayAdapter)

        binding.spinnerPosition.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
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

        binding.toolbar.navigationIcon =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_arrow_back_24, theme)
        binding.toolbar.setNavigationOnClickListener {
            this@EditPlayerActivity.onBackPressedDispatcher.onBackPressed()
        }
        binding.toolbar.inflateMenu(R.menu.toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_delete -> {
                    showDialogDelete(player)
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }

        binding.submitButton.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_save_24, 0, 0,0)
        binding.submitButton.text = resources.getString(R.string.edit)
        binding.submitButton.setOnClickListener { showDialogSubmit() }


    }

    private fun submitPlayer() {
        Utils().hideSoftKeyboard(this)
        val playerName = binding.playerName.text.toString()
        val playerPrice = binding.playerPrice.text.toString().toDoubleOrNull()
        val playerScore = binding.playerScore.text.toString().toIntOrNull()

        val player = Player(0, playerName, playerPrice, playerPosition, playerScore)
        val db = getDatabase()
        db.playerDao().updatePlayer(player)
//        PlayerListActivity().updateItem(player)
        super.finish()
    }

    private fun deletePlayer(player: Player) {
        val db = getDatabase()
        db.playerDao().delete(player)
//        PlayerListActivity().removeItem(player)
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
                setPositiveButton(
                    "APLICAR"
                ) { dialog, _ ->
                    submitPlayer()
                    dialog.cancel()
                }
                setNegativeButton(
                    "CANCELAR"
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
                setPositiveButton(
                    "ELIMINAR"
                ) { dialog, _ ->
                    deletePlayer(player)
                    dialog.cancel()
                }
                setNegativeButton(
                    "CANCELAR"
                ) { dialog, _ ->
                    dialog.cancel()
                }
                setTitle("Eliminar jugador")
                setMessage("¿Estás seguro de que deseas eliminar el jugador?")
            }
            // Set other dialog properties
            // Create the AlertDialog
            builder.create()
        }?.show()
    }

}
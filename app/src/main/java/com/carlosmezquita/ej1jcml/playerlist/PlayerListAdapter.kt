package com.carlosmezquita.ej1jcml.playerlist

import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.carlosmezquita.ej1jcml.R
import com.carlosmezquita.ej1jcml.data.Player
import com.carlosmezquita.ej1jcml.data.PlayerPositions


class PlayerListAdapter(private val players: MutableList<Player>,private val onClick: (Player) -> Unit) :
    RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder>() {

//    var players = listOf<Player>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }


//    fun setPlayersList(playersNew: List<Player>) {
//        players = playersNew
//        this.notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.bind(player)
    }

    override fun getItemCount() = players.size


    class PlayerViewHolder(view: View, val onClick: (Player) -> Unit) :
        RecyclerView.ViewHolder(view) {

        val name: TextView = view.findViewById(R.id.player_name)
        val price: TextView = view.findViewById(R.id.player_price)
        val position: TextView = view.findViewById(R.id.player_position)
        val score: TextView = view.findViewById(R.id.player_score)
//        val featuredImage: ImageView = view.findViewById(R.id.featured_image)

        private var currentPlayer: Player? = null

        init {
            view.setOnClickListener {
                currentPlayer?.let {
                    onClick(it)
                }
            }
        }

        fun bind(player: Player) {
            currentPlayer = player


            name.text = player.name
            price.text = player.price.toString() + "€"

            val playerPos = when (player.position) {
                PlayerPositions.GOALKEEPER -> "PO"
                PlayerPositions.DEFENDER -> "DF"
                PlayerPositions.STRIKER -> "DL"
                PlayerPositions.MIDFIELDER -> "MC"
                else -> {
                    "NULL"
                }
            }
            position.text = playerPos
            score.text = player.score.toString() + " puntos"
        }
        }
    }
    class PlayersComparator : DiffUtil.ItemCallback<Player>() {
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem.id == newItem.id
        }
    }

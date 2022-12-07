package com.carlosmezquita.ej1jcml.playerlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.carlosmezquita.ej1jcml.R
import com.carlosmezquita.ej1jcml.data.Player
import com.carlosmezquita.ej1jcml.data.PlayerPositions

class PlayerListAdapter: RecyclerView.Adapter<PlayerListAdapter.PlayerListViewHolder>() {

    var players = listOf<Player>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerListViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_player, parent, false)
        return PlayerListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerListViewHolder, position: Int) {
        val article = players[position]
        holder.bind(article)
    }

    override fun getItemCount() = players.size


    class PlayerListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.player_name)
        val price: TextView = view.findViewById(R.id.player_price)
        val position: TextView = view.findViewById(R.id.player_position)
        val score: TextView = view.findViewById(R.id.player_score)
//        val featuredImage: ImageView = view.findViewById(R.id.featured_image)

        fun bind(player: Player) {
            name.text = player.name
            price.text = player.price.toString() +"€"

            val playerPos = when(player.position){
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
//            description.text = article.description
//            featuredImage.setImageResource(article.featuredImage)
        }
    }
}
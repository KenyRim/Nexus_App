package com.example.nexusapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nexusapp.R
import com.example.nexusapp.listener.OnClickListeners
import java.util.*

class GamesAdapter(
    private val games: List<String>,
    private val onItemClick: OnClickListeners.OnGame
) :
    RecyclerView.Adapter<GamesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_games, parent, false)
        return MyViewHolder(itemView)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.item_category_tv)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = games[position].capitalize(Locale.ROOT)
        holder.itemView.transitionName = games[position]
        holder.itemView.setOnClickListener {
            onItemClick.click(games[position] + "/", holder.itemView)
        }


        setAnimation(holder.itemView)

    }

    private fun setAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 200
        view.startAnimation(anim)
    }

    override fun getItemCount() = games.size
}
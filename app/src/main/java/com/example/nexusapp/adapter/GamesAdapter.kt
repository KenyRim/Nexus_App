package com.example.nexusapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nexusapp.R
import com.example.nexusapp.listener.OnClickListeners

class GamesAdapter(
    private val games: List<String>,
    private val onItemClick: OnClickListeners.OnGame
) :
    RecyclerView.Adapter<GamesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_categories, parent, false)
        return MyViewHolder(itemView)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView? = null

        init {
            textView = itemView.findViewById(R.id.item_category_tv)
        }


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView?.text = games[position].capitalize()
        holder.itemView.setOnClickListener {
            onItemClick.click(games[position]+"/")
        }

    }

    override fun getItemCount() = games.size
}
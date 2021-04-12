package com.example.nexusapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nexusapp.R
import com.example.nexusapp.listener.OnClickListeners


class CategoriesAdapter(
    private val categories: List<Pair<String, String>>,
    private val onItemClick: OnClickListeners.OnCategory
) :
    RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>() {

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
        holder.textView?.text = categories[position].first
        holder.itemView.transitionName = categories[position].first
        holder.itemView.setOnClickListener {
            onItemClick.click(categories[position].second,holder.itemView)
        }

        setAnimation(holder.itemView)

    }

    private fun setAnimation(view: View) {
        val anim = ScaleAnimation(
            0.0f,
            1.0f,
            0.0f,
            1.0f,
            Animation.RELATIVE_TO_PARENT,
            0.5f,
            Animation.RELATIVE_TO_PARENT,
            0.5f
        )
        anim.duration = 300
        view.startAnimation(anim)
    }

    override fun getItemCount() = categories.size
}
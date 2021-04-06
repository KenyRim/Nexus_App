package com.example.nexusapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nexusapp.R
import com.example.nexusapp.listener.OnCategoryClickListener
import com.example.nexusapp.models.CategoryModel

class CategoryAdapter(
    private val category: List<CategoryModel>,
    private val onItemClick: OnCategoryClickListener
) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
        return MyViewHolder(itemView)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView? = null
        var imageView: ImageView? = null

        init {
            textView = itemView.findViewById(R.id.item_category_tv)
            imageView = itemView.findViewById(R.id.item_category_iv)
        }


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView?.text = category[position].description

//        holder.itemView.setOnClickListener {
//            onItemClick.click(category[position].second)
//        }

    }

    override fun getItemCount() = category.size
}
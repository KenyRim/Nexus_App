package com.example.nexusapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nexusapp.R
import com.example.nexusapp.listener.OnClickListeners
import com.example.nexusapp.models.CategoryModel

class CategoryAdapter(
    private val category: List<CategoryModel>,
    private val onItemClick: OnClickListeners.OnContent
) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
        return MyViewHolder(itemView)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTv: TextView = itemView.findViewById(R.id.item_title_tv)
        var descriptionTv: TextView = itemView.findViewById(R.id.item_description_tv)
        var imageView: ImageView = itemView.findViewById(R.id.item_category_iv)


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = category[position]
        holder.titleTv.text = item.title
        holder.descriptionTv.text = item.description


        Glide.with(holder.imageView.context)
            .load(item.image).apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_vault_tec_corporation_logo)
                    .error(R.drawable.ic_vault_tec_corporation_logo)
                    .dontAnimate()
                    .skipMemoryCache(true)
            )

            .into(holder.imageView)

//        holder.itemView.setOnClickListener {
//            onItemClick.click(category[position].second)
//        }

    }

    override fun getItemCount() = category.size
}
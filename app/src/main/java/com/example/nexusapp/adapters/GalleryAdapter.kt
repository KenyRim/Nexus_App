package com.example.nexusapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nexusapp.R

class GalleryAdapter(private val res: List<String>) : RecyclerView.Adapter<GalleryAdapter.PagerVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false))

    override fun getItemCount(): Int = res.size

    class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.item_image)

    }

    override fun onBindViewHolder(holder: PagerVH, position: Int) {

        Glide.with(holder.image.context)
            .load(res[position])
            .into(holder.image)

      //  Log.e("image tumb",res[position].replace("thumbnails/",""))
        Log.e("image tumb",res[position])

    }
}
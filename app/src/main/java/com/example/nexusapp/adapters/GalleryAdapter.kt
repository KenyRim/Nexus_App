package com.example.nexusapp.adapters

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.nexusapp.R


class GalleryAdapter(private val res: List<String>) :
    RecyclerView.Adapter<GalleryAdapter.PagerVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false))

    override fun getItemCount(): Int = res.size

    class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.item_image)

    }

    override fun onBindViewHolder(holder: PagerVH, position: Int) {

        Glide.with(holder.image.context)
            .load(res[position])
            .thumbnail(0.1f)
            .into(object : CustomTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    holder.image
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })

        Log.e("image tumb", res[position])

    }
}
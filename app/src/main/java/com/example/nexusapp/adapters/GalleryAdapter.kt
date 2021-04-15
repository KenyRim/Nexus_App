package com.example.nexusapp.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.nexusapp.R
import com.example.nexusapp.constants.EMPTY_STRING
import com.example.nexusapp.utils.TouchImageView


class GalleryAdapter(
    private val images: List<String>
) :
    RecyclerView.Adapter<GalleryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_gallery, parent, false)
        return MyViewHolder(itemView)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: TouchImageView = itemView.findViewById(R.id.item_image)

        fun bind(image: String){
            Glide.with(imageView.context)
                .asBitmap()
                .load(
                    if (image.contains("svg") || image == EMPTY_STRING
                    ) "https://www.nexusmods.com/assets/images/default/tile_empty.png" else image
                )
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        imageView.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })


            itemView.setOnClickListener {
                //   onItemClick.click(category[position].url, holder.imageView, category[position].title)
            }

            imageView.setOnClickListener {

            }


        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = images[position].replace("/thumbnails", "")
        holder.bind(item)
//        val layoutParams = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
//        layoutParams.isFullSpan = true

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


    override fun getItemCount() = images.size
}
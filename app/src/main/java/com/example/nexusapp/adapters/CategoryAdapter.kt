package com.example.nexusapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
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
    private val onItemClick: OnClickListeners.OnContent,
    private val onSaveClick: OnClickListeners.SaveClick
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
        var ivSaveBtn: ImageView = itemView.findViewById(R.id.save_btn_iv)


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = category[position]
        holder.titleTv.text = item.title
        holder.descriptionTv.text = item.description
        holder.imageView.transitionName = item.url

        Glide.with(holder.imageView.context)
            .load(item.image).apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_vault_tec_corporation_logo)
                    .error(R.drawable.ic_vault_tec_corporation_logo)
                    .dontAnimate()
                    .skipMemoryCache(true)
            )

            .into(holder.imageView)

        setAnimation(holder.itemView)
        holder.itemView.setOnClickListener {
            onItemClick.click(category[position].url,holder.imageView,category[position].title)
        }

        holder.ivSaveBtn.setOnClickListener{
            onSaveClick.clickSave(CategoryModel(0,item.image,item.title,item.description ,item.url,item.pagesCnt))
        }

    }

    private fun setAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 300
        view.startAnimation(anim)
    }


    override fun getItemCount() = category.size
}
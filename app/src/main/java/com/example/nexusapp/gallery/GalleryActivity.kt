package com.example.nexusapp.gallery

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.nexusapp.R
import com.example.nexusapp.adapters.GalleryAdapter
import com.example.nexusapp.constants.IMAGES
import com.example.nexusapp.listener.OnClickListeners
import com.example.nexusapp.utils.SliderTransformer
import kotlinx.android.synthetic.main.activity_gallery.*


class GalleryActivity: AppCompatActivity(), View.OnClickListener {



    private lateinit var adapter:GalleryAdapter
    private lateinit var pager:ViewPager2
    private var images:ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        back_btn_ib.setOnClickListener{
            onBackPressed()
        }

        btn_left.setOnClickListener(this)
        btn_right.setOnClickListener(this)

        images = intent.getStringArrayListExtra(IMAGES) as ArrayList<String>

        adapter = GalleryAdapter(images)
        pager = rv_all_images
        pager.isUserInputEnabled=false
        adapter.setHasStableIds(true)
        pager.adapter = adapter
        pager.setPageTransformer(SliderTransformer(3))



        if (!images.isNullOrEmpty()) {
            for (item in images) {

            Log.e("images-----", "images $item")
            }
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            btn_left.id -> {
                if (pager.currentItem != 0) {
                    pager.setCurrentItem(pager.currentItem - 1,true)
                }else{
                    pager.setCurrentItem(adapter.itemCount,false)
                }
            }
            btn_right.id -> {
                val page = pager.currentItem+1
                if (page < images.size) {
                    Log.e("page",pager.currentItem.toString()+" из "+images.size)

                    pager.setCurrentItem(page,true)
                }else{
                    Log.e("page",page.toString()+" ВСЁ! "+images.size+ " " +pager.childCount)

                    pager.setCurrentItem(0,false)
                }
            }
        }
    }


}
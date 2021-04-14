package com.example.nexusapp.gallery

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.nexusapp.R
import com.example.nexusapp.adapters.GalleryAdapter
import com.example.nexusapp.constants.IMAGES
import com.example.nexusapp.utils.SliderTransformer
import kotlinx.android.synthetic.main.activity_gallery.*


class GalleryActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        back_btn_ib.setOnClickListener{
            onBackPressed()
        }

        val images = intent.getStringArrayListExtra(IMAGES)
        rv_all_images.adapter = GalleryAdapter(images as ArrayList)
        rv_all_images.setPageTransformer(SliderTransformer(3))


        if (!images.isNullOrEmpty()) {
            for (item in images) {

            Log.e("images-----", "images $item")
            }
        }

    }


}
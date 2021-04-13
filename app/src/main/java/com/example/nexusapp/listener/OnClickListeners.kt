package com.example.nexusapp.listener

import android.view.View
import com.example.nexusapp.models.CategoryModel


class OnClickListeners {

    interface OnCategory {
        fun click(url: String, view: View)
    }

    interface OnGame {
        fun click(gameName: String, view: View)
    }

    interface OnContent {
        fun click(url: String, view: View, title: String)
    }

    interface SaveClick {
        fun clickSave(item: CategoryModel)
    }

    interface DeleteClick {
        fun deleteClick(position:Int,url:String)
    }

    interface OnImageClick {
        fun click(imagesList: List<String>)
    }


}
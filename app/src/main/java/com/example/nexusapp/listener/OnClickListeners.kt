package com.example.nexusapp.listener

import android.view.View
import android.widget.TextView
import com.example.nexusapp.models.DbModel


class OnClickListeners {

    interface OnCategory{
        fun click(url: String, view: View)
    }

    interface OnGame{
        fun click(gameName: String, view: View)
    }

    interface OnContent{
        fun click(url: String, view: View, title: String)
    }

    interface SaveClick{
        fun clickSave(item:DbModel)
    }


}
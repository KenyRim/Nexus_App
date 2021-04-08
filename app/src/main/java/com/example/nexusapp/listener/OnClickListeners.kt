package com.example.nexusapp.listener

import android.view.View


class OnClickListeners {

    interface OnCategory{
        fun click(url: String, view: View)
    }

    interface OnGame{
        fun click(gameName: String, view: View)
    }

    interface OnContent{
        fun click(url: String, view: View)
    }


}
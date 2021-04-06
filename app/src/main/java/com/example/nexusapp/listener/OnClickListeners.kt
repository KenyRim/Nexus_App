package com.example.nexusapp.listener


class OnClickListeners {

    interface OnCategory{
        fun click(url: String)
    }

    interface OnGame{
        fun click(gameName: String)
    }

    interface OnContent{
        fun click(url: String)
    }


}
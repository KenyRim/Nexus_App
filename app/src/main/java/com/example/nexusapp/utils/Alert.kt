package com.example.nexusapp.utils

import android.widget.Toast
import com.example.nexusapp.App

object Alert {
    fun internet(){
        Toast.makeText(
            App.applicationContext(),
            "Check your internet connection!",
            Toast.LENGTH_SHORT
        ).show()
    }
}
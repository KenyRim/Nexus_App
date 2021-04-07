package com.example.nexusapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build


class Connection {
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            } else {
                val activeNetwork = connectivityManager.activeNetworkInfo
                return activeNetwork != null
            }
        if (capabilities != null) {
            return true
        }
        return false
    }
}
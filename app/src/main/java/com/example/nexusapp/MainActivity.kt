package com.example.nexusapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nexusapp.constants.FR_CATEGORIES
import com.example.nexusapp.fragments.FragmentCategories


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, FragmentCategories(), FR_CATEGORIES)
                .commit()
        }
    }

    override fun onBackPressed() {
        val myFragment = supportFragmentManager.findFragmentById(R.id.container)
        if (myFragment != null && supportFragmentManager.backStackEntryCount > 0 && myFragment != FragmentCategories()) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
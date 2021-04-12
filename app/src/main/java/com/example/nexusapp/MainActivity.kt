package com.example.nexusapp

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nexusapp.constants.FR_GAMES
import com.example.nexusapp.fragments.FragmentCategories
import com.example.nexusapp.fragments.FragmentGames
import com.example.nexusapp.utils.Connection


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.main_sub_background)
        }
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            startFragment()
        }
    }

    override fun onBackPressed() {
        if(Connection().isOnline(this)) {
            val myFragment = supportFragmentManager.findFragmentById(R.id.container)
            if (myFragment != null && supportFragmentManager.backStackEntryCount > 0 && myFragment != FragmentGames()) {
                supportFragmentManager.popBackStack()
            } else {
                super.onBackPressed()
            }
        }else{
            startFragment()
        }
    }

    private fun startFragment(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, FragmentGames(), FR_GAMES)
            .commit()
    }
}
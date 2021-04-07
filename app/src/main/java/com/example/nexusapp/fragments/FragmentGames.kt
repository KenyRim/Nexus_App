package com.example.nexusapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.nexusapp.App
import com.example.nexusapp.R
import com.example.nexusapp.adapter.GamesAdapter
import com.example.nexusapp.constants.FR_CATEGORIES
import com.example.nexusapp.constants.FR_GAMES
import com.example.nexusapp.listener.OnClickListeners
import com.example.nexusapp.utils.Connection
import kotlinx.android.synthetic.main.fragment_games.view.*


class FragmentGames : Fragment() , OnClickListeners.OnGame{

    private lateinit var adapter: GamesAdapter
    private var rvGames: RecyclerView? = null
    val gamesList: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_games, container, false)

        gamesList.add("gta5")
        gamesList.add("fallout76")
        gamesList.add("fallout4")
        gamesList.add("newvegas")
        gamesList.add("fallout3")
        gamesList.add("skyrim")
        gamesList.add("skyrimspecialedition")
        gamesList.add("oblivion")
        gamesList.add("morrowind")
        gamesList.add("masseffect")
        gamesList.add("masseffect2")
        gamesList.add("minecraft")
        gamesList.add("witcher3")
        gamesList.add("witcher2")
        gamesList.add("cyberpunk2077")


        rvGames = rootView.rv_games
        rvGames.run {
           adapter = GamesAdapter(gamesList, this@FragmentGames)
            rvGames?.adapter = adapter

        }




        return rootView
    }

    override fun click(gameName: String) {
        if (Connection().isOnline(App.applicationContext()))
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.add(R.id.container, FragmentCategories().newInstance(gameName), FR_CATEGORIES)
            ?.addToBackStack(FR_GAMES)
            ?.commit()
        else
            Toast.makeText(App.applicationContext(),"Check your internet connection!",Toast.LENGTH_SHORT).show()
    }


}
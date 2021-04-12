package com.example.nexusapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.nexusapp.App
import com.example.nexusapp.R
import com.example.nexusapp.adapters.GamesAdapter
import com.example.nexusapp.constants.FR_CATEGORIES
import com.example.nexusapp.constants.FR_GAMES
import com.example.nexusapp.listener.OnClickListeners
import com.example.nexusapp.utils.Connection
import kotlinx.android.synthetic.main.fragment_games.view.*
import kotlinx.android.synthetic.main.main_titlebar.*
import kotlinx.android.synthetic.main.main_titlebar.view.*


class FragmentGames : Fragment(), OnClickListeners.OnGame {

    private lateinit var adapter: GamesAdapter
    private var rvGames: RecyclerView? = null
    lateinit var gamesList: ArrayList<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_games, container, false)

        gamesList = ArrayList()
        val list = arrayOf("gta5","fallout76",
            "fallout4",
            "newvegas",
            "fallout3",
            "skyrim",
            "skyrimspecialedition",
            "oblivion",
            "morrowind",
            "masseffect",
            "masseffect2",
            "minecraft",
            "witcher3",
            "witcher2",
        "cyberpunk2077")

        gamesList.addAll(list)


        rvGames = rootView.rv_games
        rvGames.run {
            adapter = GamesAdapter(gamesList, this@FragmentGames)
            rvGames?.adapter = adapter

        }

        rootView.saved_btn_iv.setOnClickListener{
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.setReorderingAllowed(true)
                ?.replace(
                    R.id.container,
                    FragmentDatabase(),
                    FR_CATEGORIES
                )
                ?.addToBackStack(FR_GAMES)
                ?.commit()
        }


        return rootView
    }

    override fun click(gameName: String, view: View) {
        if (Connection().isOnline(App.applicationContext()))
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.setReorderingAllowed(true)
             //   ?.hide(this)
                ?.addSharedElement(view, view.transitionName)
                //   ?.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                ?.replace(
                    R.id.container,
                    FragmentCategories().newInstance(gameName, view.transitionName),
                    FR_CATEGORIES
                )
                ?.addToBackStack(FR_GAMES)
                ?.commit()
        else
            Toast.makeText(
                App.applicationContext(),
                "Check your internet connection!",
                Toast.LENGTH_SHORT
            ).show()
    }


}
package com.example.nexusapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.nexusapp.R
import com.example.nexusapp.adapter.GamesAdapter
import kotlinx.android.synthetic.main.fragment_games.view.*


class FragmentGames : Fragment() {

    private lateinit var adapter: GamesAdapter
    private var rvCategories: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_games, container, false)

        rvCategories = rootView.rv_games




        return rootView
    }


}
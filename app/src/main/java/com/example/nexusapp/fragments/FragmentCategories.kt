package com.example.nexusapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.nexusapp.R
import com.example.nexusapp.adapter.CategoriesAdapter
import com.example.nexusapp.constants.*
import com.example.nexusapp.listener.OnClickListeners
import com.example.nexusapp.listener.OnResultListeners
import com.example.nexusapp.parser.CategoriesParser
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_categories.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO


class FragmentCategories : Fragment(), OnResultListeners.Categories, OnClickListeners.OnCategory {

    private lateinit var adapter: CategoriesAdapter
    private var rvCategories: RecyclerView? = null

    fun newInstance(gameName: String): FragmentCategories {
        val args = Bundle()
        args.putString(GAMES, gameName)
        val fragment = FragmentCategories()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_categories, container, false)

        rvCategories = rootView.rv_categories


        GlobalScope.launch(IO) {
            CategoriesParser().parse(
                BASE_URL + arguments?.getString(GAMES) + SECTION + CATEGORIES,
                CATEGORIES_HTML_PATH,
                this@FragmentCategories
            )
        }

        return rootView
    }

    override fun getResult(data: List<Pair<String, String>>) {

        rvCategories?.post {
            adapter = CategoriesAdapter(data, this@FragmentCategories)
            rvCategories?.adapter = adapter
        }


        for (item in data) {
            Log.e("categories", "category - ${item.first} - ${item.second}")
        }

    }

    override fun click(url: String) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.add(R.id.container, FragmentCategory().newInstance(url), FR_CATEGORY)
            ?.addToBackStack(FR_CATEGORIES)
            ?.commit()
    }

}
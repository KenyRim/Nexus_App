package com.example.nexusapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.nexusapp.App
import com.example.nexusapp.R
import com.example.nexusapp.adapter.CategoriesAdapter
import com.example.nexusapp.constants.*
import com.example.nexusapp.listener.OnCategoryClickListener
import com.example.nexusapp.listener.OnResultListener
import com.example.nexusapp.parser.CategoryParser
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_categories.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO


class FragmentCategories: Fragment(), OnResultListener, OnCategoryClickListener {

    private lateinit var adapter:CategoriesAdapter
    private var rvCategories: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_categories, container, false)

        rvCategories = rootView.rv_categories


        GlobalScope.launch(IO) {
            CategoryParser().parse(
                BASE_URL + FALLOUT4 + SECTION + CATEGORIES,
                CATEGORIES_HTML_PATH,
                this@FragmentCategories
            )
        }

        return rootView
    }

    override fun getResult(data: List<Pair<String,String>>) {

        rvCategories?.post{
            adapter = CategoriesAdapter(data,this@FragmentCategories)
            rvCategories?.adapter = adapter
        }


        for (item in data){
            Log.e("categories", "category - ${item.first} - ${item.second}")
        }

    }

    override fun click(data: String) {
       Toast.makeText(App.applicationContext(),data,Toast.LENGTH_SHORT).show()
    }

}
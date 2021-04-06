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
import com.example.nexusapp.adapter.CategoryAdapter
import com.example.nexusapp.constants.*
import com.example.nexusapp.listener.OnCategoryClickListener
import com.example.nexusapp.listener.OnResultListeners
import com.example.nexusapp.models.CategoryModel
import com.example.nexusapp.parser.CategoryParser
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_categories.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO


class FragmentCategory: Fragment(), OnResultListeners.Category, OnCategoryClickListener {

    private lateinit var adapter: CategoryAdapter
    private var rvCategories: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_category, container, false)

        rvCategories = rootView.rv_categories


        GlobalScope.launch(IO) {
            CategoryParser().parse(
                BASE_URL + FALLOUT4 + SECTION + CATEGORIES,
                CATEGORIES_HTML_PATH,
                this@FragmentCategory
            )
        }

        return rootView
    }

    override fun getResult(data: List<CategoryModel>) {

        rvCategories?.post{
            adapter = CategoryAdapter(data,this@FragmentCategory)
            rvCategories?.adapter = adapter
        }


        for (item in data){
            Log.e("categories", "category - ${item.image} - ${item.title}")
        }

    }

    override fun click(data: String) {
       Toast.makeText(App.applicationContext(),data,Toast.LENGTH_SHORT).show()
    }

}
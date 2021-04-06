package com.example.nexusapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nexusapp.App
import com.example.nexusapp.R
import com.example.nexusapp.adapter.CategoryAdapter
import com.example.nexusapp.adapter.PaginationScrollListener
import com.example.nexusapp.constants.*
import com.example.nexusapp.listener.OnCategoryClickListener
import com.example.nexusapp.listener.OnResultListeners
import com.example.nexusapp.models.CategoryModel
import com.example.nexusapp.parser.CategoryParser
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_categories.view.*
import kotlinx.android.synthetic.main.fragment_category.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO


class FragmentCategory : Fragment(), OnResultListeners.Category, OnCategoryClickListener {

    private lateinit var adapter: CategoryAdapter
    private lateinit var rvCategory: RecyclerView

    private var currentPage = 1
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    private lateinit var lm: LinearLayoutManager


    fun newInstance(categoryUrl: String): FragmentCategory {
        val args = Bundle()
        args.putString(URL, categoryUrl)
        val fragment = FragmentCategory()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_category, container, false)

        lm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvCategory = rootView.rv_category
        rvCategory.layoutManager = lm

        loadContent(currentPage)

        return rootView
    }

    private fun loadContent(page: Int) {
        Log.e("aaaaaaaa", "loadContent")
        GlobalScope.launch(IO) {
            CategoryParser().parse(
                arguments?.getString(URL) + "/?page=" + page,
                this@FragmentCategory
            )
        }
    }

    override fun getResult(data: List<CategoryModel>) {
        isLoading = false
        rvCategory.post {
            adapter = CategoryAdapter(data, this@FragmentCategory)
            rvCategory.adapter = adapter
            rvCategory.addOnScrollListener(object : PaginationScrollListener(lm) {
                override fun isLastPage(): Boolean {
                    return isLastPage
                }

                override fun isLoading(): Boolean {
                    return isLoading
                }

                override fun loadMoreItems() {
                    isLoading = true
                    Log.e("loadMoreItems", "loadMoreItems")
                    currentPage += 1
                    loadContent(currentPage)
                }
            })
        }

        for (item in data) {
            Log.e("categories", "category - ${item.image} - ${item.title}")
        }

    }

    override fun click(data: String) {
        Toast.makeText(App.applicationContext(), data, Toast.LENGTH_SHORT).show()
    }

}
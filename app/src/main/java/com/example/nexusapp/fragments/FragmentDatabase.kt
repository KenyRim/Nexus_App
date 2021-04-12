package com.example.nexusapp.fragments

import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nexusapp.App
import com.example.nexusapp.R
import com.example.nexusapp.adapters.CategoryAdapter
import com.example.nexusapp.adapters.PaginationScrollListener
import com.example.nexusapp.constants.*
import com.example.nexusapp.listener.OnClickListeners
import com.example.nexusapp.listener.OnResultListeners
import com.example.nexusapp.models.CategoryModel
import com.example.nexusapp.parser.Parser
import com.example.nexusapp.utils.Connection
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_category.view.*
import kotlinx.android.synthetic.main.titlebar.progressbar
import kotlinx.android.synthetic.main.titlebar.view.top_bar
import kotlinx.android.synthetic.main.titlebar.view.title_tv
import kotlinx.android.synthetic.main.titlebar.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.util.*
import kotlin.collections.ArrayList


class FragmentDatabase : Fragment(), OnClickListeners.OnContent {

    private var contentList: ArrayList<CategoryModel> = ArrayList()
    private lateinit var adapter: CategoryAdapter
    private lateinit var rvCategory: RecyclerView
    private lateinit var rootLayout: ConstraintLayout

    private var currentPage = 1
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private var pagesCount: Int = 0
    private lateinit var lm: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

    }


    fun newInstance(categoryUrl: String, viewTransitionName: String): FragmentDatabase {
        val args = Bundle()
        args.putString(URL, categoryUrl)
        args.putString(VIEW_TRANSITION_NAME, viewTransitionName)
        val fragment = FragmentDatabase()
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val fade = Fade()
            fade.excludeTarget(android.R.id.statusBarBackground, true)
            fade.excludeTarget(android.R.id.navigationBarBackground, true)
            sharedElementEnterTransition = TransitionInflater.from(context)
                .inflateTransition(R.transition.simple_fragment_transition)

            sharedElementReturnTransition = TransitionInflater.from(context)
                .inflateTransition(R.transition.simple_fragment_transition)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_category, container, false)

        val name = arguments?.getString(VIEW_TRANSITION_NAME)
        rootView.title_tv.text = name?.capitalize(Locale.getDefault())
        rootView.back_btn_iv.setOnClickListener {
            activity?.onBackPressed()
        }

        lm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvCategory = rootView.rv_category
        rootLayout = rootView.root_view
        rvCategory.layoutManager = lm

        rvCategory.post {
            adapter = CategoryAdapter(contentList, this@FragmentDatabase)
            rvCategory.adapter = adapter

        }

        return rootView
    }

    fun snackBar() {
        Snackbar
            .make(rootLayout, "Check your internet connection!", Snackbar.LENGTH_INDEFINITE)
            .setAction(
                "try again"
            ) {
                if (Connection().isOnline(App.applicationContext())) {
                    currentPage++
                    isLoading = false
                } else {
                    snackBar()
                }
            }
            .show()


    }


    override fun click(data: String, view: View, title: String) {
        if (Connection().isOnline(App.applicationContext()))
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.setReorderingAllowed(true)
                //   ?.hide(this)
                ?.addSharedElement(view, view.transitionName)
                // ?.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                ?.replace(
                    R.id.container,
                    FragmentArticle().newInstance(data, view.transitionName, title),
                    FR_CATEGORY
                )
                ?.addToBackStack(FR_CATEGORIES)
                ?.commit()
        else
            Toast.makeText(
                App.applicationContext(),
                "Check your internet connection!",
                Toast.LENGTH_SHORT
            ).show()

    }


}




package com.example.nexusapp.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.nexusapp.App
import com.example.nexusapp.R
import com.example.nexusapp.adapter.CategoriesAdapter
import com.example.nexusapp.constants.*
import com.example.nexusapp.listener.OnClickListeners
import com.example.nexusapp.listener.OnResultListeners
import com.example.nexusapp.parser.CategoriesParser
import com.example.nexusapp.utils.Connection
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_categories.view.*
import kotlinx.android.synthetic.main.titlebar.*
import kotlinx.android.synthetic.main.titlebar.view.*
import kotlinx.android.synthetic.main.titlebar.view.top_bar
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.util.*


class FragmentCategories : Fragment(), OnResultListeners.Categories, OnClickListeners.OnCategory {

    private lateinit var adapter: CategoriesAdapter
    private var rvCategories: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

    }

    fun newInstance(gameName: String, viewTransitionName: String): FragmentCategories {
        val args = Bundle()
        args.putString(GAMES, gameName)
        args.putString(VIEW_TRANSITION_NAME, viewTransitionName)
        val fragment = FragmentCategories()
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
        val rootView = inflater.inflate(R.layout.fragment_categories, container, false)

        rvCategories = rootView.rv_categories
        val name = arguments?.getString(VIEW_TRANSITION_NAME)
        rootView.title_tv.text = name?.capitalize(Locale.getDefault())
        rootView.top_bar.transitionName = name

        rootView.back_btn_iv.setOnClickListener {
            activity?.onBackPressed()
        }

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
            progressbar.visibility = View.GONE
        }

    }

    override fun click(url: String, view: View) {

        if (Connection().isOnline(App.applicationContext()))
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.setReorderingAllowed(true)
                //   ?.hide(this)
                ?.addSharedElement(view, view.transitionName)
               // ?.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                ?.replace(R.id.container, FragmentCategory().newInstance(url, view.transitionName), FR_CATEGORY)
                ?.addToBackStack(FR_CATEGORIES)
                ?.commit()
        else
            Toast.makeText(
                App.applicationContext(),
                "Check your internet connection!",
                Toast.LENGTH_SHORT
            ).show()


//        if (Connection().isOnline(App.applicationContext()))
//            activity?.supportFragmentManager
//                ?.beginTransaction()
//            //    ?.setReorderingAllowed(true)
//                ?.addSharedElement(view, view.transitionName)
//             //   ?.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
//                ?.replace(R.id.container, FragmentCategory().newInstance(url,view.transitionName), FR_CATEGORY)
//                ?.addToBackStack(FR_CATEGORIES)
//                ?.commit()
//        else
//            Toast.makeText(
//                App.applicationContext(),
//                "Check your internet connection!",
//                Toast.LENGTH_SHORT
//            ).show()


    }

}
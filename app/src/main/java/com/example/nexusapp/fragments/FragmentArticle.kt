package com.example.nexusapp.fragments

import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import com.example.nexusapp.R
import com.example.nexusapp.constants.*
import com.example.nexusapp.listener.OnResultListeners
import com.example.nexusapp.models.ArticleModel
import com.example.nexusapp.parser.ArticleParser
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_category.view.*
import kotlinx.android.synthetic.main.titlebar.*
import kotlinx.android.synthetic.main.titlebar.view.*
import kotlinx.android.synthetic.main.titlebar.view.top_bar
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.util.*


class FragmentArticle : Fragment(), OnResultListeners.FullArticle {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

    }


    fun newInstance(categoryUrl: String, viewTransitionName: String): FragmentArticle {
        val args = Bundle()
        args.putString(URL, categoryUrl)
        args.putString(VIEW_TRANSITION_NAME, viewTransitionName)
        val fragment = FragmentArticle()
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
        val rootView = inflater.inflate(R.layout.fragment_article, container, false)

        val name = arguments?.getString(VIEW_TRANSITION_NAME)
        rootView.title_tv.text = name?.capitalize(Locale.getDefault())
        rootView.top_bar.transitionName = name
        rootView.back_btn_iv.setOnClickListener {
            activity?.onBackPressed()
        }

       loadContent()
        return rootView
    }


    private fun loadContent() {
        view?.progressbar?.visibility = View.VISIBLE
        GlobalScope.launch(IO) {
            ArticleParser().parse(
                arguments?.getString(URL).toString(),
                this@FragmentArticle
            )
        }
    }


    override fun getResult(data: ArticleModel) {
      //  TODO("Not yet implemented")

      //  Log.e("result",data.)
    }


}



